package org.firstinspires.ftc.teamcode.pedro;

import com.pedropathing.algorithm.Foresight;
import com.pedropathing.algorithm.ForesightConfig;
import com.pedropathing.controllers.Controller;
import com.pedropathing.follower.Follower;
import com.pedropathing.math.Matrix;
import com.pedropathing.math.Vector2D;
import com.pedropathing.revhub.drivetrains.Mecanum;
import com.pedropathing.revhub.drivetrains.MecanumConfig;
import com.pedropathing.revhub.localizers.PinpointConfig;
import com.pedropathing.revhub.localizers.PinpointLocalizer;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Constants {

    public static MecanumConfig drivetrainConfig = new MecanumConfig(c -> {
        c.frontLeftName.set("frontLeftMotor");
        c.frontRightName.set("frontRightMotor");
        c.backLeftName.set("backLeftMotor");
        c.backRightName.set("backRightMotor");
        c.frontLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.frontRightDirection.set(DcMotorSimple.Direction.FORWARD);
        c.backLeftDirection.set(DcMotorSimple.Direction.REVERSE);
        c.backRightDirection.set(DcMotorSimple.Direction.FORWARD);
    });

    public static PinpointConfig localizerConfig = new PinpointConfig(c -> {
        c.name.set("pinpoint");
        c.podType.set(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        c.xPodOffset.set(3.7359904491995266);
        c.yPodOffset.set(2.0870564678522547);
        c.xPodDirection.set(GoBildaPinpointDriver.EncoderDirection.FORWARD);
        c.yPodDirection.set(GoBildaPinpointDriver.EncoderDirection.REVERSED);
        c.globalDistanceUnit.set(DistanceUnit.INCH);
        c.offsetUnits.set(DistanceUnit.INCH);
    });

    public static ForesightConfig foresightConfig = new ForesightConfig(
            c -> {
                Controller primaryTranslationalForward = Controller.proportional(0.20029037422148313);
                Controller secondaryTranslationalForward = Controller.proportional(0.07400194141747225);
                Controller primaryTranslationalLateral = Controller.proportional(0.2303557951363885);
                Controller secondaryTranslationalLateral = Controller.proportional(0.08511031108268721);

                c.forwardTranslational.set(Controller.piecewise(secondaryTranslationalForward).put(2.5, primaryTranslationalForward));
                c.strafeTranslational.set(Controller.piecewise(secondaryTranslationalLateral).put(2.5, primaryTranslationalLateral));

                c.coast.set(Controller.proportionalFeedforward(0.017572669018713374));
                c.brake.set(Controller.proportionalFeedforward(0.014936768665906368));

                c.headingFeedback.set(Controller.proportional(3.9317672345315144));
                c.headingBrakeCoefficients.set(Vector2D.cartesian(0.04911036604646407, 0.006148018144549872));

                c.linearBrakeCoefficients.set(Matrix.diag(0.0707283645907521, 0.077299189541612));
                c.quadraticBrakeCoefficients.set(Matrix.diag(0.0013073845962937871, 0.0011476788151958375));

                c.maxAchievableForwardVelocity.set(62.513901794539905);
                c.maxAchievableStrafeVelocity.set(50.14892266184695);
                c.naturalForwardDeceleration.set(28.94108014841079);
                c.naturalStrafeDeceleration.set(57.35913674656002);
            }
    );

    public static Follower create(HardwareMap h) {
        return new Follower(
                new PinpointLocalizer(h, localizerConfig),
                new Mecanum(h, drivetrainConfig),
                new Foresight(foresightConfig)
        );
    }
}