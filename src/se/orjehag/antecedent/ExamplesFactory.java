package se.orjehag.antecedent;

import se.orjehag.antecedent.placeable.Text;
import se.orjehag.antecedent.placeable.logical.flop.Dflop;
import se.orjehag.antecedent.placeable.logical.flop.Tflop;
import se.orjehag.antecedent.placeable.logical.gate.AndGate;
import se.orjehag.antecedent.placeable.logical.gate.NandGate;
import se.orjehag.antecedent.placeable.logical.gate.OrGate;
import se.orjehag.antecedent.placeable.logical.gate.XOrGate;
import se.orjehag.antecedent.placeable.logical.input.Button;
import se.orjehag.antecedent.placeable.logical.input.Clock;
import se.orjehag.antecedent.placeable.logical.input.High;
import se.orjehag.antecedent.placeable.logical.input.Switch;
import se.orjehag.antecedent.placeable.logical.output.FourBitDisplay;
import se.orjehag.antecedent.placeable.logical.output.Lamp;

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

	Text exampleLabel = new Text(400, 100);
	exampleLabel.setText("Full adder");
	simulation.add(exampleLabel);

	Text cinLabel = new Text(100, 165);
	cinLabel.setText("Carry in:");
	simulation.add(cinLabel);

	Switch cin = new Switch(100, 200);
	simulation.add(cin);

	Text iaLabel = new Text(100, 265);
	iaLabel.setText("Input A:");
	simulation.add(iaLabel);

	Switch ia = new Switch(100, 300);
	simulation.add(ia);

	Text ibLabel = new Text(100, 365);
	ibLabel.setText("Input B:");
	simulation.add(ibLabel);

	Switch ib = new Switch(100, 400);
	simulation.add(ib);

	XOrGate xor1 = new XOrGate(300, 340);
	simulation.add(xor1);
	xor1.inputs.get(0).connectTo(ia.outputs.get(0));
	xor1.inputs.get(1).connectTo(ib.outputs.get(0));

	AndGate and1 = new AndGate(300, 400);
	simulation.add(and1);
	and1.inputs.get(0).connectTo(ia.outputs.get(0));
	and1.inputs.get(1).connectTo(ib.outputs.get(0));

	XOrGate xor2 = new XOrGate(450, 200);
	simulation.add(xor2);
	xor2.inputs.get(0).connectTo(cin.outputs.get(0));
	xor2.inputs.get(1).connectTo(xor1.outputs.get(0));

	AndGate and2 = new AndGate(450, 260);
	simulation.add(and2);
	and2.inputs.get(0).connectTo(cin.outputs.get(0));
	and2.inputs.get(1).connectTo(xor1.outputs.get(0));

	OrGate or = new OrGate(550, 350);
	simulation.add(or);
	or.inputs.get(0).connectTo(and2.outputs.get(0));
	or.inputs.get(1).connectTo(and1.outputs.get(0));

	Text sumLabel = new Text(700, 165);
	sumLabel.setText("Sum:");
	simulation.add(sumLabel);

	Lamp sum = new Lamp(700, 200);
	simulation.add(sum);
	sum.inputs.get(0).connectTo(xor2.outputs.get(0));

	Text coutLabel = new Text(700, 265);
	coutLabel.setText("Carry out:");
	simulation.add(coutLabel);

	Lamp cout = new Lamp(700, 300);
	simulation.add(cout);
	cout.inputs.get(0).connectTo(or.outputs.get(0));

	return simulation;
    }

    public static Simulation dFlipFlop() {
	Simulation simulation = new Simulation();

	Text exampleLabel = new Text(400, 100);
	exampleLabel.setText("D Flipflop");
	simulation.add(exampleLabel);

	Text dinLabel = new Text(100, 165);
	dinLabel.setText("Data:");
	simulation.add(dinLabel);

	Switch din = new Switch(100, 200);
	simulation.add(din);

	Text clkLabel = new Text(100, 265);
	clkLabel.setText("Clock:");
	simulation.add(clkLabel);

	Button clk = new Button(100, 300);
	simulation.add(clk);

	NandGate nand1 = new NandGate(300, 200);
	simulation.add(nand1);

	NandGate nand2 = new NandGate(300, 300);
	simulation.add(nand2);

	NandGate nand3 = new NandGate(500, 200);
	simulation.add(nand3);

	NandGate nand4 = new NandGate(500, 300);
	simulation.add(nand4);

	Text qLabel = new Text(700, 165);
	qLabel.setText("Q:");
	simulation.add(qLabel);

	Lamp qout = new Lamp(700, 200);
	simulation.add(qout);

	nand1.inputs.get(0).connectTo(din.outputs.get(0));
	nand1.inputs.get(1).connectTo(clk.outputs.get(0));
	nand2.inputs.get(0).connectTo(clk.outputs.get(0));
	nand2.inputs.get(1).connectTo(nand1.outputs.get(0));
	nand3.inputs.get(0).connectTo(nand1.outputs.get(0));
	nand3.inputs.get(1).connectTo(nand4.outputs.get(0));
	nand4.inputs.get(0).connectTo(nand3.outputs.get(0));
	nand4.inputs.get(1).connectTo(nand2.outputs.get(0));
	qout.inputs.get(0).connectTo(nand3.outputs.get(0));

	return simulation;
    }

    public static Simulation clickCounter() {
	Simulation simulation = new Simulation();

	Text exampleLabel = new Text(400, 100);
	exampleLabel.setText("Click counter");
	simulation.add(exampleLabel);

	High high = new High(70, 200);
	simulation.add(high);

	Text btnLabel = new Text(70, 500);
	btnLabel.setText("Click this!");
	simulation.add(btnLabel);

	Button btn = new Button(70, 550);
	simulation.add(btn);

	Tflop flop1 = new Tflop(200, 300);
	simulation.add(flop1);

	Tflop flop2 = new Tflop(320, 350);
	simulation.add(flop2);

	AndGate and1 = new AndGate(450, 250);
	simulation.add(and1);

	Tflop flop3 = new Tflop(550, 330);
	simulation.add(flop3);

	AndGate and2 = new AndGate(650, 250);
	simulation.add(and2);

	Tflop flop4 = new Tflop(750, 350);
	simulation.add(flop4);

	FourBitDisplay display = new FourBitDisplay(900, 500);
	simulation.add(display);

	flop1.inputs.get(0).connectTo(high.outputs.get(0));
	flop1.inputs.get(1).connectTo(btn.outputs.get(0));
	flop2.inputs.get(1).connectTo(btn.outputs.get(0));
	flop3.inputs.get(1).connectTo(btn.outputs.get(0));
	flop4.inputs.get(1).connectTo(btn.outputs.get(0));
	and1.inputs.get(0).connectTo(flop1.outputs.get(0));
	and1.inputs.get(1).connectTo(flop2.outputs.get(0));
	and2.inputs.get(0).connectTo(and1.outputs.get(0));
	and2.inputs.get(1).connectTo(flop3.outputs.get(0));
	flop4.inputs.get(0).connectTo(and2.outputs.get(0));
	flop2.inputs.get(0).connectTo(flop1.outputs.get(0));
	flop3.inputs.get(0).connectTo(and1.outputs.get(0));
	display.inputs.get(0).connectTo(flop1.outputs.get(0));
	display.inputs.get(1).connectTo(flop2.outputs.get(0));
	display.inputs.get(2).connectTo(flop3.outputs.get(0));
	display.inputs.get(3).connectTo(flop4.outputs.get(0));

	return simulation;
    }

    public static Simulation cascadingFlipFlops() {
	Simulation simulation = new Simulation();

	Text exampleLabel = new Text(400, 100);
	exampleLabel.setText("Cascading flip flops");
	simulation.add(exampleLabel);

	Switch sw = new Switch(100, 200);
	simulation.add(sw);

	Clock clock = new Clock(100, 400);
	simulation.add(clock);

	Dflop flop1 = new Dflop(250, 225);
	simulation.add(flop1);

	Dflop flop2 = new Dflop(400, 250);
	simulation.add(flop2);

	Dflop flop3 = new Dflop(550, 275);
	simulation.add(flop3);

	Dflop flop4 = new Dflop(700, 300);
	simulation.add(flop4);

	Lamp lamp1 = new Lamp(400, 500);
	simulation.add(lamp1);

	Lamp lamp2 = new Lamp(550, 500);
	simulation.add(lamp2);

	Lamp lamp3 = new Lamp(700, 500);
	simulation.add(lamp3);

	Lamp lamp4 = new Lamp(850, 500);
	simulation.add(lamp4);

	flop1.inputs.get(0).connectTo(sw.outputs.get(0));
	flop1.inputs.get(1).connectTo(clock.outputs.get(0));
	flop2.inputs.get(0).connectTo(flop1.outputs.get(0));
	flop2.inputs.get(1).connectTo(clock.outputs.get(0));
	flop3.inputs.get(0).connectTo(flop2.outputs.get(0));
	flop3.inputs.get(1).connectTo(clock.outputs.get(0));
	flop4.inputs.get(0).connectTo(flop3.outputs.get(0));
	flop4.inputs.get(1).connectTo(clock.outputs.get(0));
	lamp1.inputs.get(0).connectTo(flop1.outputs.get(0));
	lamp2.inputs.get(0).connectTo(flop2.outputs.get(0));
	lamp3.inputs.get(0).connectTo(flop3.outputs.get(0));
	lamp4.inputs.get(0).connectTo(flop4.outputs.get(0));

	return simulation;
    }

    public static Simulation introSim() {
	Simulation simulation = new Simulation();

	Text exampleLabel = new Text(500, 100);
	exampleLabel.setText("Introduction");
	simulation.add(exampleLabel);

	Text text1 = new Text(500, 150);
	text1.setText("Drag items from the left and drop them here to add them to the circuit.");
	simulation.add(text1);

	Text text2 = new Text(500, 180);
	text2.setText("Remove items by first selecting them and then pressing backspace or delete.");
	simulation.add(text2);

	Text text3 = new Text(500, 210);
	text3.setText("Change properties of items like the clock or text by first selecting them and then right clicking on them.");
	simulation.add(text3);

	Text text5 = new Text(300, 270);
	text5.setText("Flick this switch:");
	simulation.add(text5);

	Switch sw = new Switch(300, 300);
	simulation.add(sw);

	Text text6 = new Text(300, 370);
	text6.setText("Right click clock:");
	simulation.add(text6);

	Clock clock = new Clock(300, 400);
	simulation.add(clock);

	OrGate or = new OrGate(450, 350);
	simulation.add(or);

	Lamp lamp = new Lamp(600, 325);
	simulation.add(lamp);

	lamp.inputs.get(0).connectTo(or.outputs.get(0));
	or.inputs.get(0).connectTo(sw.outputs.get(0));
	or.inputs.get(1).connectTo(clock.outputs.get(0));

	Text text4 = new Text(500, 500);
	text4.setText("Remove all items in the simulation by clicking \"Edit -> Remove all\" in the menu above.");
	simulation.add(text4);

	return simulation;
    }
}
