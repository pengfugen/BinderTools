// IPicServer.aidl
package wind.pfg.bindertools;

// Declare any non-default types here with import statements
import wind.pfg.bindertools.PicData;
import wind.pfg.bindertools.ICallBack;

interface IPicServer {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
     PicData getPicture(String path);
     void setCallBack(in ICallBack callback);
}
