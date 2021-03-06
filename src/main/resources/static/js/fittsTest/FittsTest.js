/**
 * Created by Thomas on 19/11/2015.
 */
function FittsTest(stages)
{
    this.testStages = [];
    this.currentStage = {};
    this.currentStageNumber = 0;
    this.canvas = {};
    this.testFinished = false;

    for(var i = 0; i < stages.length; i++)
    {
        this.testStages[i] = new FittsTestStage(stages[i].numberOfDots, stages[i].dotRadius, stages[i].dotDistance);
    }

    this.initialize = function(canvas)
    {
        this.canvas = canvas;
        this.currentStageNumber = -1;
        this.testFinished = false;

        this.nextStage();
    };

    this.draw = function(context)
    {
        //Draw border

        //Draw position circle

        //Draw target circles
        this.currentStage.drawDots(context);

        //Draw tracking path

    };

    this.getCurrentStageNumber = function()
    {
        return this.currentStageNumber;
    };

    this.getCurrentStage = function()
    {
        return this.currentStage
    };

    this.nextStage = function()
    {
        if(this.currentStageNumber < (this.testStages.length - 1))
        {
            this.currentStageNumber++;

            //Select random stage from list
            var found = false;

            while(!found)
            {
                var randomStageNumber = Math.floor((Math.random() * this.testStages.length));

                if(!this.testStages[randomStageNumber].getFinished())
                {
                    //Not completed test stage found
                    found = true;
                }
            }

            //Get selected random stage
            this.currentStage = this.testStages[randomStageNumber];

            //Add index number for running order
            this.currentStage.setStageOrderIndex(this.currentStageNumber);

            this.currentStage.initialize(this.canvas);

            return this.currentStage;
        }
        else
        {
            this.testFinished = true;
            return null;
        }
    };

    this.getTestStages = function()
    {
        return this.testStages;
    };

    this.getNumberOfStages = function()
    {
        return this.testStages.length;
    };

    this.getTestFinished = function()
    {
        return this.testFinished;
    };

    this.repositionTest = function()
    {
        this.currentStage.repositionTest(this.canvas);
    };

    //Temporary function
    this.getThroughput = function()
    {
        var We = this.testStages[0].dotRadius*2;
        var d = this.testStages[0].dotDistance*2;
        var difficutlyIndex = Math.log((d/We)+1)/Math.log(2);
        var movementTime = this.testStages[0].getTrackPaths()[this.testStages[0].getTrackPaths().length-1].getLastEvent().getTimestamp() -
            this.testStages[0].getTrackPaths()[0].getFirstEvent().getTimestamp();
        var totalTime = (movementTime/1000)/this.testStages[0].numberOfDots;
        return difficutlyIndex/totalTime;
    };
}