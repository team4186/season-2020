package frc.subsystems.vision;

public interface VisionRunner {
    void init();

    void periodic();

    boolean hasTarget();

    double xOffset();

    double getAlignX();

    double yOffset();

    double height();

    double getDistance();
}
