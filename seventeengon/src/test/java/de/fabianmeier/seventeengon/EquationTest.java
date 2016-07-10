package de.fabianmeier.seventeengon;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import de.fabianmeier.seventeengon.intersection.DMan;
import de.fabianmeier.seventeengon.intersection.EquationSolver;

/**
 * tests Equation
 * 
 * @author jfabi
 *
 */
public class EquationTest
{

	/**
	 * 
	 */
	@Test
	public void testSolveQuadraticEquation()
	{
		Set<Double> result = EquationSolver.solveQuadraticEquation(1, 2, 1);

		assertTrue(result.size() == 1);

		for (Double d : result)
		{
			assertTrue(DMan.same(-1, d));
		}
	}

	/**
	 * 
	 */
	@Test
	public void testSolveLinearSystem()
	{
		double[][] coefficients = new double[2][2];
		coefficients[0][0] = 1;
		coefficients[0][1] = 0;
		coefficients[1][0] = 0;
		coefficients[1][1] = 2;

		double[] rhs = new double[2];

		rhs[0] = 3;
		rhs[1] = 10;

		double[] result = EquationSolver.solveLinearSystem(coefficients, rhs);

		assertTrue(DMan.same(result[0], 3));
		assertTrue(DMan.same(result[1], 5));
	}

	/**
	 * 
	 */
	@Test
	public void testSolveLinearSystem2()
	{
		double[][] coefficients = new double[2][2];
		coefficients[0][0] = 0;
		coefficients[0][1] = 1;
		coefficients[1][0] = 2;
		coefficients[1][1] = 0;

		double[] rhs = new double[2];

		rhs[0] = 3;
		rhs[1] = 10;

		double[] result = EquationSolver.solveLinearSystem(coefficients, rhs);

		assertTrue(DMan.same(result[0], 5));
		assertTrue(DMan.same(result[1], 3));
	}

	/**
	 * 
	 */
	@Test
	public void testDet()
	{
		double[][] matrix = new double[2][2];
		matrix[0][0] = 2;
		matrix[1][1] = 2;
		matrix[1][0] = 1;
		matrix[0][1] = -1;

		double dete = EquationSolver.det(matrix);

		assertTrue(DMan.same(dete, 5));
	}

}
