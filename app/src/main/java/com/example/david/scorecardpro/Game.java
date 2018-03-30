package com.example.david.scorecardpro;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.david.GameListener;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by david on 10/3/2017.
 */

public class Game extends AppCompatActivity
{

    static Player player1 = new Player("David", "Walsh", 1, 22);
    static Player player2 = new Player("Jack", "Lavallee", 2, 24);
    static Player player3 = new Player("Joe", "Russell", 3, 35);
    static Player player4 = new Player("Craig", "Damon", 4, 55);
    static Player player5 = new Player("Leslie", "Damon", 5, 50);
    static Player player6 = new Player("Peter", "Chapin", 6, 55);
    static Player player7 = new Player("Mike", "Hall", 7, 22);
    static Player player8 = new Player("Matt", "Tanneberger", 8, 21);
    static Player player9 = new Player("Jake", "Morrill", 9, 22);

    static PositionsInGame pitcherPosition = new PositionsInGame(player1, Positions.PITCHER);
    static PositionsInGame catcherPosition = new PositionsInGame(player2, Positions.CATCHER);
    static PositionsInGame firstBasePosition = new PositionsInGame(player3, Positions.FIRSTBASE);
    static PositionsInGame secondBasePosition = new PositionsInGame(player4, Positions.SECONDBASE);
    static PositionsInGame thirdBasePosition = new PositionsInGame(player5, Positions.THIRDBASE);
    static PositionsInGame shortStopPosition = new PositionsInGame(player6, Positions.SHORTSTOP);
    static PositionsInGame centerFieldPosition = new PositionsInGame(player7, Positions.LEFTFIELD);
    static PositionsInGame rightFieldPosition = new PositionsInGame(player8, Positions.CENTERFIELD);
    static PositionsInGame leftFieldPosition = new PositionsInGame(player9, Positions.RIGHTFIELD);

    ArrayList <Player> homeTeamBattingOrder = new ArrayList<>(Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9));
    ArrayList <Player> awayTeamBattingOrder = new ArrayList<>(Arrays.asList(player1, player2, player3, player4, player5, player6, player7, player8, player9));

    Field field = new Field(firstBasePosition, secondBasePosition, thirdBasePosition, shortStopPosition, centerFieldPosition, leftFieldPosition, rightFieldPosition, catcherPosition, pitcherPosition);

    ArrayList <PositionsInGame> homeTeamPositions = new ArrayList<>(Arrays.asList(pitcherPosition, firstBasePosition, catcherPosition, secondBasePosition, shortStopPosition, thirdBasePosition, centerFieldPosition, leftFieldPosition, rightFieldPosition));
    ArrayList <PositionsInGame> awayTeamPositions = new ArrayList<>(Arrays.asList(pitcherPosition, firstBasePosition, catcherPosition, secondBasePosition, shortStopPosition, thirdBasePosition, centerFieldPosition, leftFieldPosition, rightFieldPosition));


    DatabaseHandler db = new DatabaseHandler(this);

    public static Game getById (int id) { return mostRecentGame; }

    public void setHomeTeam (Team homeTeam)
    {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam (Team awayTeam)
    {
        this.awayTeam = awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void incrementHomeTeamScore () { homeTeamScore++; }

    public void incrementAwayTeamScore () { awayTeamScore++; }

    public GameType getGameType () { return gameType; }

    public TeamInGame getGameWinner()
    {
        if (homeTeamScore > awayTeamScore)
        {
            return homeTeamInGame;
        }
        else
            return awayTeamInGame;
    }

    public TeamInGame getGameLoser ()
    {
        if (awayTeamScore > homeTeamScore )
        {
            return awayTeamInGame;
        }
        else
            return homeTeamInGame;
    }

    public void addInning (Inning inning)
    {
        innings.add(innings.size(), inning);
    }

    public void addPlay (Play playToAdd)
    {
        plays.add(playToAdd);
    }

    public ArrayList<Play> getPlays () { return plays; }

    public TeamInGame getHomeTeamInGame () { return homeTeamInGame; }

    public TeamInGame getAwayTeamInGame () { return awayTeamInGame; }

    public void setHomeTeamInGame (TeamInGame homeTeamInGame)
    {
        this.homeTeamInGame = homeTeamInGame;
    }

    public void setAwayTeamInGame (TeamInGame awayTeamInGame)
    {
        this.awayTeamInGame = awayTeamInGame;
    }

    public ArrayList<Player> getHomeTeamBattingOrder () { return homeTeamBattingOrder; }

    public ArrayList<Player> getAwayTeamBattingOrder () { return awayTeamBattingOrder; }

    public Team getHomeTeam () { return homeTeam; }

    public Team getAwayTeam () { return awayTeam; }

    public int getTopOrBottom () { return topOrBottom; }

    public void setCurrentlyTopOfInning ()
    {
        topOrBottom = 1;
    }

    public void setCurrentlyBottomOfInning ()
    {
        topOrBottom = 2;
    }

    public AtBat getCurrentBatter () { return currentBatter; }

    public void setCurrentBatter (AtBat currentBatter)
    {
        this.currentBatter = currentBatter;
    }

    public HalfInning getCurrentHalfInning () { return currentHalfInning; }

    public void setCurrentHalfInning (HalfInning currentHalfInning)
    {
        this.currentHalfInning = currentHalfInning;
    }

    public Inning getCurrentInning () { return currentInning; }

    public void setCurrentInning (Inning currentInning)
    {
        this.currentInning = currentInning;
    }

    public int getHomeTeamBattingOrderPosition () { return homeTeamBattingOrderPosition; }

    public void setHomeTeamBattingOrderPosition (int homeTeamBattingOrderPosition)
    {
        this.homeTeamBattingOrderPosition = homeTeamBattingOrderPosition;
    }

    public int getAwayTeamBattingOrderPosition () { return awayTeamBattingOrderPosition; }

    public void setAwayTeamBattingOrderPosition (int awayTeamBattingOrderPosition)
    {
        this.awayTeamBattingOrderPosition = awayTeamBattingOrderPosition;
    }

    public int getCurrentBattingOrderPosition () { return currentBattingOrderPosition; }

    public void setCurrentBattingOrderPosition (int currentBattingOrderPosition)
    {
        this.currentBattingOrderPosition = currentBattingOrderPosition;
    }

    public int getCurrentInningCount () { return currentInningCount; }

    public void setCurrentInningCount (int currentInningCount)
    {
        this.currentInningCount = currentInningCount;
    }

    public void setCurrentBattingOrder (ArrayList<Player> currentBattingOrder)
    {
        this.currentBattingOrder = currentBattingOrder;
    }

    public ArrayList<Player> getCurrentBattingOrder () { return currentBattingOrder; }

    public void ball ()
    {
        if (getCurrentBatter().getBallCount() < 3)
        {
            getCurrentBatter().incrementBalls();
            createNewPlay("BALL", false);
            System.out.println("Ball");
            System.out.println("Current ball count is " + getCurrentBatter().getBallCount());
            if (getCurrentBatter().getBallCount() == 1) {
                Log.i("Balls", "" + getCurrentBatter().getBallCount());
                for (GameListener gl : gameListeners)
                {
                    gl.ballCalled(1);
                }
            }
            else if (getCurrentBatter().getBallCount() == 2) {
                Log.i("Balls", "" + getCurrentBatter().getBallCount());
                for (GameListener gl : gameListeners)
                {
                    gl.ballCalled(2);
                }
            }
            else if (getCurrentBatter().getBallCount() == 3) {
                Log.i("Balls", "" + getCurrentBatter().getBallCount());
                for (GameListener gl : gameListeners)
                {
                    gl.ballCalled(3);
                }
            }
        }
        else
        {
            getCurrentBatter().incrementBalls();
            createNewPlay("BALL",false);
            currentPlay.setPlayText("BB");
            System.out.println("Ball");
            System.out.println("4 balls, New currentBatter is set");
            walk();
            setNewBatter();
        }
    }

    public void walk ()
    {
        advanceBase(getCurrentBatter().getPlayer(), basePath.getHomeBase(), basePath.getFirstBase());
    }

    public void advanceBase (Player player, Base currentBase, Base nextBase)
    {
        /*System.batterOut.println("=====================================");
        System.batterOut.println("We hit advanceBase");
        System.batterOut.println("The player is " + player.getFullName());
        System.batterOut.println("The currentBase is " + currentBase.getBaseNumber());
        System.batterOut.println("The nextBase is " + nextBase.getBaseNumber());
        System.batterOut.println("Does nextBase have a runner on it " + nextBase.doesBaseHaveRunner());
        System.batterOut.println("=====================================");*/

        if (nextBase.doesBaseHaveRunner())
        {
            advanceBase(nextBase.getRunnerOnBase(), nextBase, basePath.getNextBase(nextBase));
        }

        if (nextBase == basePath.getHomeBase())
        {
            newRunnerAction(false, currentBase, nextBase, player, true);
            getCurrentHalfInning().incrementRunsScored();
            currentBase.removeRunnerOnBase();
            incrementRunsScored();
            Log.i("advanceBase", "Run Scored by: " + player.getFullName());
        }

        else
        {
            newRunnerAction(false, currentBase, nextBase, player, false);
            nextBase.setRunnerOnBase(player);
            Log.i("AdvanceBase", "Someone is on base " + nextBase.getBaseNumber() + " " + player.getFullName());
        }
    }

    public void removeRunnerFromBase (Base base)
    {
        basePath.removeRunnerFromBase(base);
        unMarkBase(base);
    }

    public void unMarkBase (Base baseToUnMark)
    {
        if (baseToUnMark.getRunnerOnBase() != null) {
            baseToUnMark.getRunnerOnBase().noLongerOnBase(baseToUnMark);
            Log.i("unMarkBase", "base unmarked:" + baseToUnMark.getBaseNumber());
        }
        else {
            return;
        }
    }

    public void markBase (Base baseToMark, Player player)
    {
        player.nowOnBase(baseToMark);
        Log.i("markBase", "base marked:" + baseToMark.getBaseNumber());

    }

    public Play createNewPlay (String pitch, boolean out)
    {
        if (getTopOrBottom() == 1)
        {
            Play newPlay = new Play(getCurrentBatter().getPlayer(), pitcherPosition.getPlayer(), Pitch.valueOf(pitch), getCurrentBatter(), getCurrentInningCount(), getCurrentBattingOrderPosition(), 1, out);
            currentPlay = newPlay;
            addPlay(newPlay);
            db.toDB(newPlay);
            return newPlay;
        }

        else
        {
            Play newPlay = new Play(getCurrentBatter().getPlayer(), pitcherPosition.getPlayer(), Pitch.valueOf(pitch), getCurrentBatter(), getCurrentInningCount(), getCurrentBattingOrderPosition(), 2,  out);
            currentPlay = newPlay;
            addPlay(currentPlay);
            db.toDB(newPlay);
            return newPlay;
        }
    }

    public void strike ()
    {
        if (getCurrentBatter().getStrikeCount() < 2)
        {
            getCurrentBatter().incrementStrikes();
            createNewPlay("STRIKE", false);
            System.out.println("Strike");
            System.out.println("Current strike count is " + getCurrentBatter().getStrikeCount());
            if (getCurrentBatter().getStrikeCount() == 1) {
                Log.i("Strikes", "" + getCurrentBatter().getStrikeCount());
                for (GameListener gl : gameListeners)
                {
                    gl.strikeCalled(1);
                }
            }
            else if (getCurrentBatter().getStrikeCount() == 2) {
                Log.i("Strikes", "" + getCurrentBatter().getStrikeCount());
                for (GameListener gl : gameListeners)
                {
                    gl.strikeCalled(2);
                }
            }
        }
        else
        {
            getCurrentBatter().incrementStrikes();
            createNewPlay("STRIKE", true);
            currentPlay.setPlayText("K");
            System.out.println("Strike");
            System.out.println("3 Strikes, increment outs and set new current batter");
            batterOut();
        }
    }

    public void foul ()
    {
        //    createNewPlay("FOUL");
        if (getCurrentBatter().getStrikeCount() < 2)
        {
            System.out.println("Foul ball");
            System.out.println("Current strike count is " + getCurrentBatter().getStrikeCount());
            getCurrentBatter().incrementStrikes();
            if (getCurrentBatter().getStrikeCount() == 1) {
                for (GameListener gl : gameListeners)
                {
                    gl.strikeCalled(1);
                }
            }
            else if (getCurrentBatter().getStrikeCount() == 2) {
                for (GameListener gl : gameListeners)
                {
                    gl.strikeCalled(2);
                }
            }
        }
    }

    public void runnerOut(Base base, Player player) {
        newRunnerAction(true, basePath.getPreviousBase(base), base, player, false);
        outOccurred();
        base.removeRunnerOnBase();
    }

    public void batterOut()
    {
        outOccurred();
        setNewBatter();
    }

    private void outOccurred() {
        incrementOuts();
        System.out.println("Out ");
        System.out.println("Current batterOut count is " + getCurrentHalfInning().getOuts());
        if (getCurrentHalfInning().getOuts() == 1) {
            for (GameListener gl : gameListeners)
            {
                gl.outOccured(1);
            }
        }
        else if (getCurrentHalfInning().getOuts() == 2) {
            for (GameListener gl : gameListeners)
            {
                gl.outOccured(2);
            }
        }
        else {
            for (GameListener gl : gameListeners)
            {
                gl.inningEnded();
            }
        }
    }

    public void setNewBatter ()
    {
        Log.i("setNewBatter", "new batter = " + getCurrentBatter().getPlayer().getFullName());
        if (getCurrentBatter() == null)
        {
            System.out.println("Current batter is null");
        }

        if (getCurrentHalfInning() == null)
        {
            System.out.println("Current halfinning is null");
        }
        getCurrentHalfInning().addAtBat(getCurrentBatter());
        for (GameListener gl : gameListeners)
        {
            gl.atBatEnded();
        }
        incrementBattingOrderPosition(getCurrentBattingOrderPosition());
        removeRunnerFromBase(basePath.getHomeBase());
        setCurrentBatter(new AtBat(getCurrentHalfInning(), currentBattingOrder.get(getCurrentBattingOrderPosition())));
        System.out.println("A new batter has been set. It is " + getCurrentBatter().getPlayer().getFullName());
    }

    public void incrementBattingOrderPosition (int oldPosition)
    {
        if (oldPosition < 8)
        {
            System.out.println("Batting Order Position has been incremented. Position before incrementation = " + getCurrentBattingOrderPosition());
            setCurrentBattingOrderPosition(getCurrentBattingOrderPosition() + 1);
            System.out.println("New position is " + getCurrentBattingOrderPosition());
        }

        else
        {
            System.out.println("Batting Order Position has been incremented. Position before incrementation = " + getCurrentBattingOrderPosition());
            setCurrentBattingOrderPosition(0);
            System.out.println("New position is " + getCurrentBattingOrderPosition());
        }
    }

    public void incrementOuts ()
    {

        if (getCurrentBatter().getHalfInning().getOuts() < 2)
        {
            System.out.println("Current batterOut count is " + getCurrentBatter().getHalfInning().getOuts());
            getCurrentHalfInning().incrementOuts();
            setCurrentBatter( new AtBat(getCurrentHalfInning(), currentBattingOrder.get(getCurrentBattingOrderPosition())));
        }

        else
        {
            System.out.println("There are now 3 outs, setting up next half inning");
            System.out.println("Current Inning Count is " + getCurrentInning().getInningCount());

            for (GameListener gl : gameListeners)
            {
                gl.inningEnded();
            }

            if (getCurrentHalfInning().topOrBottom() == 1) //currently the top of the inning
            {
                setCurrentlyBottomOfInning();
                System.out.println("The new half inning will be the bottom of the " + getCurrentInning().getInningCount());
                getCurrentInning().setTopInning(getCurrentHalfInning());
                setCurrentHalfInning( new HalfInning(awayTeam, homeTeam, 2, getCurrentInning().getInningCount()));
            }
            else
            {
                setCurrentlyTopOfInning();
                System.out.println("Game type number of innings = " + getGameType().getNumInnings());
                System.out.println("Current innning = " + getCurrentInning().getInningCount());
                if (getCurrentInning().getInningCount() == getGameType().getNumInnings())
                {
                    System.out.println("The game is over since the max innings have been reached!");
                    endGame();
                }
                else
                {
                    System.out.println("New Inning. Setting up top of the new inning. The inning number is " + getCurrentInning().getInningCount());
                    getCurrentInning().setBottomInning(getCurrentHalfInning());
                    setCurrentInningCount(getCurrentInningCount() + 1);
                    setCurrentHalfInning( new HalfInning(homeTeam, awayTeam, 1, getCurrentInning().getInningCount()));
                    setCurrentInning( new Inning(this, getCurrentInning().getInningCount(), getCurrentHalfInning(), null));
                    addInning(getCurrentInning());

                    startHalfInning(getCurrentHalfInning());
                }

            }

            setCurrentFieldingPositions();
            setCurrentBattingOrder(currentBattingOrder);
        }
    }

    public void setCurrentFieldingPositions ()
    {
        if (getTopOrBottom() == 1)
        {
            awayTeamPositions = currentFieldingPositions;
            currentFieldingPositions = homeTeamPositions;
        }
        else
        {
            homeTeamPositions = currentFieldingPositions;
            currentFieldingPositions = awayTeamPositions;
        }
    }

    public void startHalfInning (HalfInning currentHalfInning)
    {
        setCurrentBatter(new AtBat(currentHalfInning, getCurrentBattingOrder().get(getCurrentBattingOrderPosition())));
    //    setFieldTextViews();
    }

    public void endGame ()
    {
        for (GameListener gl : gameListeners)
        {
            gl.gameEnded();
        }

        Context context = getApplicationContext();
        System.out.println("The winner of the game is " + getGameWinner().toString());
        CharSequence text = "The winner of the game is " + getGameWinner().toString();
        int duration = Toast.LENGTH_SHORT;

        Toast gameWinnerToast = Toast.makeText(context, text, duration);
        gameWinnerToast.show();
    }

    public void incrementRunsScored ()
    {
        if (getCurrentHalfInning().topOrBottom() == 1)
        {
            incrementAwayTeamScore();
            System.out.println("The away team score has been incremented");
            for (GameListener gl : gameListeners)
            {
                gl.awayRunsScored(awayTeamScore);
            }
        }
        else
        {
            incrementHomeTeamScore();
            System.out.println("The home team score has been incremented");
            for (GameListener gl : gameListeners)
            {
                gl.homeRunsScored(homeTeamScore);
            }
        }
    }

    public void newRunnerAction (boolean out, Base startingBase, Base endingBase, Player runner, boolean scored)
    {
        if (getTopOrBottom() == 1) {
            RunnerEvent newRunnerEvent = new RunnerEvent(getCurrentInningCount(), getCurrentBattingOrderPosition(), getCurrentBatter(), out, startingBase, endingBase, runner, awayTeamBattingOrder.indexOf(runner) + 1, scored);

            /*System.batterOut.println("=====================================");
            System.batterOut.println("Batting Order Position = " + getCurrentBattingOrderPosition() + " Current Batter is = " + getCurrentBatter().getPlayer().getFullName());
            System.batterOut.println("Starting Base = " + startingBase.getBaseNumber() + " Ending Base = " + endingBase.getBaseNumber());
            System.batterOut.println("Runner is " + runner.getFullName() + " Current Batting Order Position = " + awayTeamBattingOrder.indexOf(runner) + 1);
            System.batterOut.println("=====================================");*/

            currentPlay.addRunnerEvent(newRunnerEvent);
        }

        if (getTopOrBottom() == 2) {
            RunnerEvent newRunnerEvent = new RunnerEvent(getCurrentInningCount(), getCurrentBattingOrderPosition(), getCurrentBatter(), out, startingBase, endingBase, runner, getHomeTeamBattingOrder().indexOf(runner) + 1, scored);
            currentPlay.addRunnerEvent(newRunnerEvent);
        }
        //     System.batterOut.println("A new runner action has been created!");
    }

    public void repOk() {
        assert homeTeam != null;
        assert awayTeam != null;
        assert homeTeamScore > -1;
        assert awayTeamScore > -1;
        assert innings != null;
    }

    public Game(Team homeTeamInGame, Team awayTeamInGame, GameType gameType)
    {
        this.homeTeamInGame = new TeamInGame(homeTeamInGame);
        this.awayTeamInGame = new TeamInGame(awayTeamInGame);
        this.gameType = gameType;
        this.innings = new ArrayList<>();
        this.topOrBottom = 1;
        this.currentInningCount = 1;
        homeTeamBattingOrderPosition = 0;
        awayTeamBattingOrderPosition = 0;
        mostRecentGame = this;
        this.plays= new ArrayList<>();
        this.gameListeners = new ArrayList<>();
        this.currentBattingOrder = new ArrayList<>();
        repOk();
    }

    public void removeListener(GameListener gameListener)
    {
        this.gameListeners.remove(gameListener);
    }

    public void addListener (GameListener gameListener)
    {
        this.gameListeners.add(gameListener);
    }

    public ArrayList<Inning> getInnings () { return innings; }

    BasePath basePath = new BasePath();
    Play currentPlay;
    ArrayList <PositionsInGame> currentFieldingPositions = null;

    private GameType gameType;
    private TeamInGame homeTeamInGame;
    private int homeTeamScore;
    private int awayTeamScore;
    private TeamInGame awayTeamInGame;
    private Team homeTeam;
    private Team awayTeam;
    private static Game mostRecentGame;
    private ArrayList <Inning> innings;
    private int topOrBottom;
    private ArrayList <Play> plays;
    private AtBat currentBatter;
    private HalfInning currentHalfInning;
    private Inning currentInning;
    private int homeTeamBattingOrderPosition;
    private int awayTeamBattingOrderPosition;
    private int currentBattingOrderPosition;
    private int currentInningCount;
    private ArrayList<GameListener> gameListeners;
    private ArrayList<Player> currentBattingOrder;
}
