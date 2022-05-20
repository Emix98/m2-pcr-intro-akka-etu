package m2dl.pcr.akka.eratosthenes;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class System {

    public static final Logger log = LoggerFactory.getLogger(System.class);

    public static void main(String... args) throws Exception {

        final ActorSystem as = ActorSystem.create("actor-system");

        //On attend pour être sûr que les acteurs sont bien créés
        Thread.sleep(5000);

        final ActorRef firstCrible = as.actorOf(Props.create(CribleActor.class, 2), "crible2-actor");

        for (int i = 3 ; i < 100 ; i++) {
            firstCrible.tell(i, null);
        }

        Thread.sleep(3000);

        log.debug("Actor System Shutdown Starting...");

        as.terminate();
    }

}
