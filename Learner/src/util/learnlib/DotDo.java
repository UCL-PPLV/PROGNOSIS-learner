package util.learnlib;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import net.automatalib.automata.FiniteAlphabetAutomaton;
import net.automatalib.automata.transducers.MealyMachine;
import net.automatalib.automata.transducers.impl.compact.CompactMealy;
import net.automatalib.serialization.InputModelData;
import net.automatalib.words.Alphabet;
import util.ExceptionAdapter;

import net.automatalib.serialization.dot.DOTParsers;
import net.automatalib.serialization.InputModelDeserializer;
import net.automatalib.serialization.dot.GraphDOT;

public class DotDo {
	// policy : convert into method throwing unchecked exception
	public static <O> MealyMachine<?, String, ?, O> readDotFile(String filepath)  {
		try {
			return readFile(filepath);
		} catch (IOException ex) {
			throw new ExceptionAdapter(ex);
		}
	}

	public static <O> MealyMachine<?, String, ?, O> readFile(String filename) throws IOException {
		File file = new File(filename);
		InputModelDeserializer<String, CompactMealy<String,String>> parser = DOTParsers.mealy();
		InputModelData<String, CompactMealy<String, String>> machine = parser.readModel(file);
		return (MealyMachine<?, String, ?, O>) machine.model;
	}

    // policy : convert into method throwing unchecked exception
	public static <O> void writeDotFile(MealyMachine<?, String, ?, O> automaton, Collection<? extends String> inputAlphabet, String filepath) throws IOException {
		writeFile(automaton, inputAlphabet, filepath);
	}

	//policy:
	//  write dotfile with red double circeled start state
	public static <O> void writeFile(MealyMachine<?, String, ?, O> automaton, Collection<? extends String> inputAlphabet, String filepath) throws IOException {
		BufferedWriter outstream = new BufferedWriter(new FileWriter(filepath));
		write(automaton, inputAlphabet, outstream);
		outstream.close();
	}

	/* write
	 *   same as writeFile but then to Appendable instead of filepath
	 *
	 */
	public static <O> void write(MealyMachine<?, String, ?, O> automaton, Collection<? extends String> inputAlphabet, Appendable out) {
		try {
			GraphDOT.write(automaton, inputAlphabet, out);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ExceptionAdapter(e);
		}
	}
}
