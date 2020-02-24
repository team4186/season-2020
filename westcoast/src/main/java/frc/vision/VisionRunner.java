package frc.vision;
public interface VisionRunner {
    public void init();
    public boolean hasTarget();
    public double xOffset();
    public double getAlignX();
    public double yOffset();
    public double height();
    public double getDistance();
}
