package m2dl.pcr.akka.eratosthenes;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.TypedActor;
import akka.actor.UntypedActor;
import akka.japi.Procedure;
import m2dl.pcr.akka.helloworld4.HelloActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CribleActor extends UntypedActor {

    public static final Logger log = LoggerFactory.getLogger(CribleActor.class);

    private int value;
    private ActorRef next;

    Procedure<Object> end = new Procedure<Object>() {
        @Override
        public void apply(Object o) throws Exception {
            if (o instanceof Integer) {
                int testedNumber = (Integer) o;
                if (testedNumber % value != 0) {
                    next = getContext().actorOf(Props.create(CribleActor.class, testedNumber), "crible" + testedNumber + "-actor");
                    getContext().become(inter, false);
                }
            } else {
                unhandled(o);
            }
        }
    };

    Procedure<Object> inter = new Procedure<Object>() {
        @Override
        public void apply(Object o) throws Exception {
            if (o instanceof Integer) {
                int testedNumber = (Integer) o;
                if (testedNumber % value != 0) {
                    next.tell(o, getSelf());
                }
            } else {
                unhandled(o);
            }
        }
    };

    public CribleActor(int value) {
        log.info("Creating crible " + value);
        this.value = value;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        end.apply(o);
    }
}
