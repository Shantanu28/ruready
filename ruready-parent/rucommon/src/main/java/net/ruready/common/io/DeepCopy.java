package net.ruready.common.io;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;


/**
 * @author Nava L. Livne <i><nlivne@aoce.utah.edu></i>
 *          Academic Outreach and Continuing Education (AOCE)
 *          1901 East South Campus Dr., Room 2197-E
 *          University of Utah, Salt Lake City, UT 84112
 * @author Oren E. Livne <i><olivne@aoce.utah.edu></i>
 *          AOCE, Room 2197-E, University of Utah
 *            
 *          University of Utah, Salt Lake City, UT 84112
 * Protected by U.S. Provisional Patent U-4003, February 2006
 * 
 * @version 21/12/2005
 * 
 * Utility for making deep copies (vs. clone()'s shallow copies) of 
 * objects. Objects are first serialized and then deserialized. Error
 * checking is fairly minimal in this implementation. If an object is
 * encountered that cannot be serialized (or that references an object
 * that cannot be serialized) an error is printed to System.err and
 * null is returned. Depending on your specific application, it might
 * make more sense to have copy(...) re-throw the exception.
 *
 */
public class DeepCopy {

    /**
     * Returns a copy of the object, or null if the object cannot
     * be serialized.
     */
    public static Object copy(Object orig) {
        Object obj = null;
        try {
            // Write the object out to a byte array
            FastByteArrayOutputStream fbos = 
                    new FastByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(fbos);
            out.writeObject(orig);
            out.flush();
            out.close();

            // Retrieve an input stream from the byte array and read
            // a copy of the object back in. 
            ObjectInputStream in = 
                new ObjectInputStream(fbos.getInputStream());
            obj = in.readObject();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ClassNotFoundException cnfe) {
            cnfe.printStackTrace();
        }
        return obj;
    }

}