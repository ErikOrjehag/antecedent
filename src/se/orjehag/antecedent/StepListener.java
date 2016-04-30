package se.orjehag.antecedent;

/**
 * Classes that want to be notified by simulation steps will implement this.
 */
public interface StepListener
{
    void onStep();
}
