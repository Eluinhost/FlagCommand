package gg.uhc.flagcommands.commands;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DequoterTest {

    @Test
    public void test() {
        Dequoter dequoter = new Dequoter();

        String[] test = "-s \"Test String\" -d yes \"\\\"\" pineapple\\ sunday".split(" ");

        String[] deq = dequoter.dequote(test);

        assertThat(deq).containsExactly("-s", "Test String", "-d", "yes", "\"", "pineapple sunday");
    }
}
