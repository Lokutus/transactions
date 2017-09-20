package name.jikra.interview.transaction.events;

import org.springframework.context.ApplicationEvent;

public class Signal extends ApplicationEvent {

    public Signal(SignalType source) {
        super(source);
    }

    @Override
    public SignalType getSource() {
        return (SignalType) source;
    }
}
