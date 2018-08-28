/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\fpeng3\\BinderTools\\app\\src\\main\\aidl\\wind\\pfg\\bindertools\\IPicServer.aidl
 */
package wind.pfg.bindertools;
public interface IPicServer extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements wind.pfg.bindertools.IPicServer
{
private static final java.lang.String DESCRIPTOR = "wind.pfg.bindertools.IPicServer";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an wind.pfg.bindertools.IPicServer interface,
 * generating a proxy if needed.
 */
public static wind.pfg.bindertools.IPicServer asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof wind.pfg.bindertools.IPicServer))) {
return ((wind.pfg.bindertools.IPicServer)iin);
}
return new wind.pfg.bindertools.IPicServer.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_getPicture:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
wind.pfg.bindertools.PicData _result = this.getPicture(_arg0);
reply.writeNoException();
if ((_result!=null)) {
reply.writeInt(1);
_result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
case TRANSACTION_setCallBack:
{
data.enforceInterface(DESCRIPTOR);
wind.pfg.bindertools.ICallBack _arg0;
_arg0 = wind.pfg.bindertools.ICallBack.Stub.asInterface(data.readStrongBinder());
this.setCallBack(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements wind.pfg.bindertools.IPicServer
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
@Override public wind.pfg.bindertools.PicData getPicture(java.lang.String path) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
wind.pfg.bindertools.PicData _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(path);
mRemote.transact(Stub.TRANSACTION_getPicture, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
_result = wind.pfg.bindertools.PicData.CREATOR.createFromParcel(_reply);
}
else {
_result = null;
}
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setCallBack(wind.pfg.bindertools.ICallBack callback) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((callback!=null))?(callback.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_setCallBack, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_getPicture = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_setCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
public wind.pfg.bindertools.PicData getPicture(java.lang.String path) throws android.os.RemoteException;
public void setCallBack(wind.pfg.bindertools.ICallBack callback) throws android.os.RemoteException;
}
