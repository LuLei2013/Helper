package com.lu.lei;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.imageio.ImageIO;

import junit.framework.Assert;

import org.sikuli.api.ColorImageTarget;
import org.sikuli.api.ScreenRegion;
import org.sikuli.api.Target;

/**
 * @author lulei lei.a.lu@ericsson.com
 * @version Jul 18, 2014 1:16:59 PM
 */
public class MainTest {
	static int captureHeight;
	static int captureWidth;
	static int rowNum;
	static int cowNum;
	static int CardHeight;
	static int CardWidth;
	static int GameX;
	static int GameY;
	static SikuliImpl sikuli = new SikuliImpl();
	static Card cards[][];
	static Coordinate previousCordinate = new Coordinate(1, 1);
	static boolean isScaned[][];
	static String capturePicDirectory;

	// initiate parameters
	static void initiateParameters() throws IOException {
		InputParams inputParams = new InputParams();
		while (!inputParams.isFinised) {
			sikuli.wait(1);
		}

		rowNum = inputParams.getRowNum();
		cowNum = inputParams.getCowNum();
		cards = new Card[rowNum + 2][cowNum + 2];
		isScaned = new boolean[rowNum + 2][cowNum + 2];

		for (int i = 0; i < rowNum + 2; i++)
			for (int j = 0; j < cowNum + 2; j++) {
				cards[i][j] = new Card();
				if (i == 0 || j == 0 || i == rowNum + 1 || j == cowNum + 1) {
					cards[i][j].setKind(0);
					isScaned[i][j] = true;
				}
			}
		capturePicDirectory = System.getProperty("user.dir");
		capturePicDirectory += File.separator + "capture.png";
		BufferedImage fatherImage = ImageIO.read(new File(capturePicDirectory));
		captureHeight = fatherImage.getHeight();
		captureWidth = fatherImage.getWidth();
		sikuli.wait(5);
		CardHeight = captureHeight / rowNum;
		CardWidth = captureWidth / cowNum;
		ScreenRegion fatherRegion = sikuli.find(capturePicDirectory);
		sikuli.wait(5);
		Assert.assertNotNull(fatherRegion);
		GameX = fatherRegion.getUpperLeftCorner().getX();
		GameY = fatherRegion.getUpperLeftCorner().getY();

		Coordinate coordinate = null;
		int kind = 1;
		while ((coordinate = getNextEmpty()) != null) {
			BufferedImage sonImage = null;
			sonImage = fatherImage.getSubimage((coordinate.getC() - 1)
					* (CardWidth + 1), (coordinate.getR() - 1)
					* (CardHeight + 1), CardWidth - cowNum + 1, CardHeight
					- rowNum + 1);
			Target finaltarget = new ColorImageTarget(sonImage);
			// set the similarity between the target region and the screenRegion
			finaltarget.setMinScore(0.8);
			List<ScreenRegion> finalRegions = sikuli.findAll(finaltarget);
			// just for debuging
			for (ScreenRegion screenRegion : finalRegions) {
				int c = (screenRegion.getCenter().getX() - GameX) / CardWidth;
				int r = (screenRegion.getCenter().getY() - GameY) / CardHeight;
				cards[r + 1][c + 1] = new Card();
				cards[r + 1][c + 1].setKind(kind);
				isScaned[r + 1][c + 1] = true;
			}
			kind++;
		}
	}

	static Coordinate getNextEmpty() {
		for (int i = 1; i < rowNum + 1; i++)
			for (int j = 1; j < cowNum + 1; j++)
				if (!isScaned[i][j])
					return new Coordinate(i, j);
		return null;
	}

	static boolean isNotVisited(Coordinate coordinate) {
		return isScaned[coordinate.getR()][coordinate.getC()];
	}

	static void clearStatus(Card[][] cards) {
		for (int i = 0; i < rowNum + 2; i++) {
			for (int j = 0; j < cowNum + 2; j++) {
				cards[i][j].setCrossNum(0);
				cards[i][j].setDirection(Direction.All);
				isScaned[i][j] = true;
			}
		}
	}

	public static boolean getNextCoordinate() {
		int nextR;
		int nextC;
		int i = 0;
		while (true) {
			i++;
			int r = previousCordinate.getR();
			int c = previousCordinate.getC();
			if (r + 1 > rowNum && c + 1 > cowNum) {
				nextR = 1;
				nextC = 1;
			} else if (c + 1 > cowNum) {
				nextR = r + 1;
				nextC = 1;
			} else {
				nextR = r;
				nextC = c + 1;
			}
			previousCordinate = new Coordinate(nextR, nextC);
			if (cards[nextR][nextC].getKind() != 0) {
				return true;
			}
			if (i >= rowNum * cowNum)
				return false;
		}

	}

	public static boolean tryToDealCurrentOne() {
		Queue<Coordinate> queue = new LinkedList<Coordinate>();
		queue.offer(previousCordinate);
		isScaned[previousCordinate.getR()][previousCordinate.getC()] = false;
		boolean isFound = false;
		Coordinate first = null;
		Coordinate second = null;
		while (!queue.isEmpty() && !isFound) {
			first = queue.poll();
			int crossNum = cards[first.getR()][first.getC()].getCrossNum();
			int crossNumTemp = crossNum;
			Direction direction = cards[first.getR()][first.getC()]
					.getDirection();
			// North
			if (first.getR() - 1 >= 0) {
				Coordinate north = new Coordinate(first.getR() - 1,
						first.getC());
				if (direction != Direction.North && direction != Direction.All) {
					crossNum++;
				}
				if (crossNum < 3) {
					if (cards[north.getR()][north.getC()].getKind() == 0
							&& isNotVisited(north)) {
						cards[north.getR()][north.getC()].setCrossNum(crossNum);
						cards[north.getR()][north.getC()]
								.setDirection(Direction.North);
						queue.offer(north);
					} else if (cards[north.getR()][north.getC()].getKind() == cards[previousCordinate
							.getR()][previousCordinate.getC()].getKind()
							&& !north.equals(previousCordinate)) {
						isFound = true;
						second = north;
						break;
					}
				}
				isScaned[north.getR()][north.getC()] = false;
			}

			crossNum = crossNumTemp;
			// South
			if (first.getR() + 1 <= rowNum + 1) {
				Coordinate south = new Coordinate(first.getR() + 1,
						first.getC());
				if (direction != Direction.South && direction != Direction.All) {
					crossNum++;
				}
				if (crossNum < 3) {
					if (cards[south.getR()][south.getC()].getKind() == 0
							&& isNotVisited(south)) {
						cards[south.getR()][south.getC()].setCrossNum(crossNum);
						cards[south.getR()][south.getC()]
								.setDirection(Direction.South);
						queue.offer(south);
					} else if (cards[south.getR()][south.getC()].getKind() == cards[previousCordinate
							.getR()][previousCordinate.getC()].getKind()
							&& !south.equals(previousCordinate)) {
						isFound = true;
						second = south;
						break;
					}
				}
				isScaned[south.getR()][south.getC()] = false;
			}
			crossNum = crossNumTemp;
			// West
			if (first.getC() - 1 >= 0) {
				Coordinate west = new Coordinate(first.getR(), first.getC() - 1);
				if (direction != Direction.West && direction != Direction.All) {
					crossNum++;
				}
				if (crossNum < 3) {
					if (cards[west.getR()][west.getC()].getKind() == 0
							&& isNotVisited(west)) {
						cards[west.getR()][west.getC()]
								.setDirection(Direction.West);
						cards[west.getR()][west.getC()].setCrossNum(crossNum);
						queue.offer(west);
					} else if (cards[west.getR()][west.getC()].getKind() == cards[previousCordinate
							.getR()][previousCordinate.getC()].getKind()
							&& !west.equals(previousCordinate)) {
						isFound = true;
						second = west;
						break;
					}
				}
				isScaned[west.getR()][west.getC()] = false;
			}
			crossNum = crossNumTemp;
			// East
			if (first.getC() + 1 <= cowNum + 1) {
				Coordinate east = new Coordinate(first.getR(), first.getC() + 1);
				if (direction != Direction.East && direction != Direction.All) {
					crossNum++;
				}
				if (crossNum < 3) {
					if (cards[east.getR()][east.getC()].getKind() == 0
							&& isNotVisited(east)) {
						cards[east.getR()][east.getC()].setCrossNum(crossNum);
						cards[east.getR()][east.getC()]
								.setDirection(Direction.East);
						queue.offer(east);
					} else if (cards[east.getR()][east.getC()].getKind() == cards[previousCordinate
							.getR()][previousCordinate.getC()].getKind()
							&& !east.equals(previousCordinate)) {
						isFound = true;
						second = east;
					}
				}
				isScaned[east.getR()][east.getC()] = false;
			}

		}
		if (isFound) {
			sikuli.click(GameX + (previousCordinate.getC() - 1) * CardWidth
					+ CardWidth / 2, GameY + (previousCordinate.getR() - 1)
					* CardHeight + CardHeight / 2);
			cards[previousCordinate.getR()][previousCordinate.getC()]
					.setKind(0);
			sikuli.wait(1);
			sikuli.click(GameX + (second.getC() - 1) * CardWidth + CardWidth
					/ 2, GameY + (second.getR() - 1) * CardHeight + CardHeight
					/ 2);
			cards[second.getR()][second.getC()].setKind(0);
		}
		clearStatus(cards);
		return isFound;
	}

	static void testDone() {
		int numDome = 0;
		while (numDome < (cowNum * rowNum) / 2) {
			if (tryToDealCurrentOne()) {
				numDome++;
			}
			if (!getNextCoordinate()) {
				break;
			}
		}
	}

	public static void main(String[] args) throws Exception {
		initiateParameters();
		testDone();
	}

}
