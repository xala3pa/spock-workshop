package org.example.training

import org.example.training.events.EventBus
import org.example.training.events.Listener
import spock.lang.Specification
import spock.util.concurrent.BlockingVariable

/**
 * These are the second set of exercises for the Spock workshop, which introduce
 * mocking and asynchronous behaviour.
 */
class Workshop02Spec extends Specification {
      def eventBus = new EventBus()

    /**
     * <p>TODO #07: Write a feature method for {@link EventBus#send(java.lang.String)}.
     * You can use Spock's mocking framework to provide fake listeners and
     * verify that they are called when the {@code send()} method is invoked.</p>
     */
     def "check that each register listener receive a messages"(){
         given: "a message"
         String message = "Spock is awesome!"

         and: "listeners registered in the bus"
         def listener1 = Mock(Listener)
         eventBus.register(listener1)

         def listener2 = Mock(Listener)
         eventBus.register(listener2)

         when: "the message is sent to the bus"
         eventBus.send(message)

         then: "the listener receive the message"
         1 * listener1.onMessage(message)
         1 * listener2.onMessage(message)
     }

    /**
     * <p>TODO #08: Write a feature method for {@link EventBus#sendAsync(java.lang.String, org.example.training.events.Callback)}.
     * Since this method sends the messages on a separate thread, you can't
     * just check a fake listener as soon as {@code sendAsync()} returns.
     * Fortunately, Spock provides a couple of features that can help:
     * {@code BlockingVariable} and {@code PollingConditions}. Either of these
     * will help you create a test that runs reliably.</p>
     */
    def "All registered listeners have to received the given message"(){
        given: "A message"
        def message = "SPock is awesome!!"

        and: "listeners registered in the bus"
        def listener1Called = false
        eventBus.register({ String msg ->
            assert msg == message
            listener1Called = true
        })

        def listener2Called = false
        eventBus.register({ String msg ->
            assert msg == message
            listener2Called = true
        })

        and: "A blocking variable that's released when send has completed"
        def completed = new BlockingVariable<Boolean>()

        when: "I send the test message to the event bus"
        eventBus.sendAsync(message, {-> completed.set(true)})

        then: "Both listeners receive that message"
        completed.get()
        listener1Called && listener2Called


    }
}
