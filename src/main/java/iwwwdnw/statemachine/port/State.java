package iwwwdnw.statemachine.port;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface State {

	boolean isSubStateOf(State state);

	boolean isSuperStateOf(State state);

	public enum S implements State {

		INIT_GAME, //

		START_TURN, //

		MAKE_A_TURN(START_TURN), //

		ROLL_DICE,

		CHOOSE_STARTFIELD,

		CHOOSE_FIGURE_TO_MOVE,
		MOVE_FIGURE,

		DUEL;

		private List<State> subStates;

		public static final S INITIAL_STATE = INIT_GAME;

		private S(State... subS) {
			this.subStates = new ArrayList<>(Arrays.asList(subS));
		}

		@Override
		public boolean isSuperStateOf(State s) {
			boolean result = (s == null) || (this == s); // self contained
			for (State state : this.subStates) // or
				result |= state.isSuperStateOf(s); // contained in a substate!
			return result;
		}

		@Override
		public boolean isSubStateOf(State state) {
			return (state != null) && state.isSuperStateOf(this);
		}
	}

}