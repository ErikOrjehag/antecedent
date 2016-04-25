package se.orjehag.antecedent;

import se.orjehag.antecedent.placable.Text;
import se.orjehag.antecedent.placable.logical.gate.AndGate;
import se.orjehag.antecedent.placable.logical.gate.OrGate;
import se.orjehag.antecedent.placable.logical.gate.XOrGate;
import se.orjehag.antecedent.placable.logical.input.Switch;
import se.orjehag.antecedent.placable.logical.output.Lamp;

/**
 * A lot of example simulations made by hand to
 * using the public API of the simulation class.
 * I made these examples to demonstrate the
 * functionality implemented. This factory
 * is used when an example is picked from the
 * menu dropdown list.
 */
public final class ExamplesFactory
{
    private ExamplesFactory() {}

    // X and Y positions of logical components are not magic numbers.
    @SuppressWarnings("MagicNumber")
    public static Simulation fullAdder() {
	Simulation simulation = new Simulation();

	Text cinLabel = new Text(100, 50);
	simulation.add(cinLabel);

	Switch cin = new Switch(100, 100);
	simulation.add(cin);

	Text iaLabel = new Text(100, 150);
	simulation.add(iaLabel);

	Switch ia = new Switch(100, 200);
	simulation.add(ia);

	Text ibLabel = new Text(100, 250);
	simulation.add(ibLabel);

	Switch ib = new Switch(100, 300);
	simulation.add(ib);

	XOrGate xor1 = new XOrGate(300, 200);
	simulation.add(xor1);
	xor1.inputs.get(0).connectTo(ia.outputs.get(0));
	xor1.inputs.get(1).connectTo(ib.outputs.get(0));

	AndGate and1 = new AndGate(300, 300);
	simulation.add(and1);
	and1.inputs.get(0).connectTo(ia.outputs.get(0));
	and1.inputs.get(1).connectTo(ib.outputs.get(0));

	XOrGate xor2 = new XOrGate(400, 200);
	simulation.add(xor2);
	xor2.inputs.get(0).connectTo(cin.outputs.get(0));
	xor2.inputs.get(1).connectTo(xor1.outputs.get(0));

	AndGate and2 = new AndGate(400, 300);
	simulation.add(and2);
	and2.inputs.get(0).connectTo(cin.outputs.get(0));
	and2.inputs.get(1).connectTo(xor1.outputs.get(0));

	OrGate or = new OrGate(500, 300);
	simulation.add(or);
	or.inputs.get(0).connectTo(and2.outputs.get(0));
	or.inputs.get(1).connectTo(and1.outputs.get(0));

	Lamp sum = new Lamp(600, 200);
	simulation.add(sum);
	sum.inputs.get(0).connectTo(xor2.outputs.get(0));

	Lamp cout = new Lamp(600, 300);
	simulation.add(cout);
	cout.inputs.get(0).connectTo(or.outputs.get(0));

	return simulation;
    }
}
