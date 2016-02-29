package de.fabianmeier.seventeengon.intersection;

import java.util.HashSet;
import java.util.Set;

public class EquationSolver
{

	public static Set<Double> solveQuadraticEquation(double a, double b,
			double c)
	{
		if (DMan.same(a, 0))
		{
			if (DMan.same(b, 0))
			{
				throw new IllegalArgumentException("Gleichung " + a + "x²+" + b
						+ "x+" + c + " nicht lösbar.");
			} else
			{
				Set<Double> result = new HashSet<Double>();
				result.add(-c / b);
				return result;
			}
		} else
		{
			double discriminant = b * b - 4 * a * c;

			Set<Double> root = DMan.squareRoot(discriminant);

			Set<Double> result = new HashSet<Double>();

			for (Double d : root)
			{
				result.add((-b + d) / (2 * a));
			}
			return result;
		}

	}

	public static double[] solveLinearSystem(double[][] coefficients,
			double[] rhs)
	{
		double detA = det(coefficients);

		if (DMan.same(detA, 0))
			return null;

		int n = coefficients[0].length;

		double[] back = new double[n];

		for (int i = 0; i < n; i++)
		{
			double[][] cramerMatrix = new double[n][n];

			for (int row = 0; row < n; row++)
				for (int column = 0; column < n; column++)
				{
					if (column == i)
					{
						cramerMatrix[row][column] = rhs[row];
					} else
					{
						cramerMatrix[row][column] = coefficients[row][column];
					}

				}

			back[i] = det(cramerMatrix) / detA;

		}

		if (checkResult(coefficients, rhs, back))
			return back;
		else
			throw new IllegalStateException("Mismatch" + back.toString() + " * "
					+ coefficients.toString() + " = " + rhs.toString());

	}

	private static boolean checkResult(double[][] coefficients, double[] rhs,
			double[] lambda)
	{
		int n = coefficients.length;

		double[] expected = new double[n];

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
			{
				expected[i] += coefficients[i][j] * lambda[j];
			}

		for (int i = 0; i < n; i++)
		{
			if (!DMan.same(expected[i], rhs[i]))
				return false;
		}

		return true;
	}

	/**
	 * Determinant of a matrix using Laplace's formula with expanding along the
	 * 0th row. It is not checked whether the matrix is quadratic!
	 * 
	 * @param m
	 *            Matrix
	 * @return determinant
	 */
	public static double det(double[][] m)
	{
		int n = m.length;
		if (n == 1)
		{
			return m[0][0];
		} else
		{
			double det = 0;
			for (int j = 0; j < n; j++)
			{
				det += Math.pow(-1, j) * m[0][j] * det(minor(m, 0, j));
			}
			return det;
		}
	}

	/**
	 * Computing the minor of the matrix m without the i-th row and the j-th
	 * column
	 * 
	 * @param m
	 *            input matrix
	 * @param i
	 *            removing the i-th row of m
	 * @param j
	 *            removing the j-th column of m
	 * @return minor of m
	 */
	private static double[][] minor(final double[][] m, final int i,
			final int j)
	{
		int n = m.length;
		double[][] minor = new double[n - 1][n - 1];
		// index for minor matrix position:
		int r = 0, s = 0;
		for (int k = 0; k < n; k++)
		{
			double[] row = m[k];
			if (k != i)
			{
				for (int l = 0; l < row.length; l++)
				{
					if (l != j)
					{
						minor[r][s++] = row[l];
					}
				}
				r++;
				s = 0;
			}
		}
		return minor;
	}

}
