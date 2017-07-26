package nuris.epam.connection;

import nuris.epam.connection.exception.ResourcesException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Custom class-list, for storing stream objects
 *
 * @author Kalenov Nurislam
 */
public class ResourcesQueue<T> {
    private static final Logger log = LoggerFactory.getLogger(ResourcesQueue.class);
    /**
     * Field - restrict access to the resource.
     */
    private Semaphore semaphore;
    /**
     * Field - storage of the list of initialized connections
     */
    private Queue<T> resource = new ConcurrentLinkedQueue<>();
    /**
     * Field - time waiting for a special connection
     */
    private final int timeOut;

    /**
     * Creates a new object with Semaphore and assigns a value to timeOut
     *
     * @param count   - semaphore counter
     * @param timeOut - time waiting for a connection
     */
    public ResourcesQueue(int count, int timeOut) {
        semaphore = new Semaphore(count, true);
        this.timeOut = timeOut;
    }

    /**
     * A method that allows you to take a connection from the list if it is available
     *
     * @return initialized connection
     */
    public T takeResource() throws ResourcesException {
        try {
            if (semaphore.tryAcquire(timeOut, TimeUnit.SECONDS)) {
                log.debug("The connection was taken");
                return resource.poll();
            }
        } catch (InterruptedException e) {
            log.warn("Didn't wait for connect", e);
            throw new ResourcesException("Didn't wait for connect", e);
        }
        throw new ResourcesException("Didn't wait for connect");
    }

    /**
     * Returns the connection back to the list of initialized connections
     */
    public void returnResource(T res) {

        resource.add(res);
        semaphore.release();
    }

    /**
     * Adds an initialized connection to the list
     */
    public void addResource(T t) {
        resource.add(t);
    }

    /**
     * The size of the list
     *
     * @return List size
     */
    public int size() {
        return resource.size();
    }

    /**
     * List of connections
     *
     * @return Returns a list
     */
    public Queue<T> getResources() {
        return resource;
    }
}


