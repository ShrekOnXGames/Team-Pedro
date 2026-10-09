package org.firstinspires.ftc.teamcode.autoOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedro.Constants;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.math.Pose;
import static com.pedropathing.api.Paths.*;
import com.pedropathing.paths.Path;
import com.pedropathing.ivy.Scheduler;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
@Autonomous
public class ExampleAuto extends OpMode {

    private Follower follower;
    private final PoseFactory p = PoseFactory.degrees();
    private final Pose startPose = p.of(55.332142857142856, 8.232857142857142, 90);
    private final Pose park = p.of(10, 90.1742857142857, 180);
    //poses from before
    private Path park() {
        return curve(startPose, controlPose, park).linear(startPose, park);
    }
    // other poses...
    private final Pose controlPose = p.of(21.409285714285733, 77.40714285714284, 150);
    private Command autoRoutine() {
        return sequential(
                follow(follower, park())
                //add "follow(follower, INSERT ACTION()),"
        );
    }
    @Override
    public void init() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(startPose);
        follower.update();
    }
    @Override
    public void start() {
        schedule(follow(follower, park()));
    }
    @Override
    public void loop() {
        follower.update();
        Scheduler.execute();
    }
}
