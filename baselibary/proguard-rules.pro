-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
 
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends java.io.Serializable
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService

-keep public class org.greenrobot.greendao.** { *; }
-keep public class qiu.niorgai.** { *; }
-keep public class com.smcb.simulatedstock.bean.** { *; }
-keep public class net.sourceforge.pinyin4j.** { *; }
-keep public class rx.** { *; }
-keep public class java.awt.** { *; }
-keep public class javax.swing.** { *; }
-keep public class android.support.** { *; }
-keep public class demo.Pinyin4jAppletDemo$* { *; }
-keep public class demo.Pinyin4jAppletDemo { *; }

-dontwarn android.support.**
-dontwarn kale.adapter.**
-dontwarn org.greenrobot.greendao.**
-dontwarn demo.Pinyin4jAppletDemo$*
-dontwarn demo.Pinyin4jAppletDemo

#gson解析不被混淆
# Gson uses generic type information stored in a class file when working with fields. Proguard
# removes such information by default, so configure it to keep all of it.
-keepattributes Signature

# Gson specific classes
-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }

# Application classes that will be serialized/deserialized over Gson
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.**{*;}
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
#如果有引用v4包可以添加下面这行
-keep public class * extends android.support.v4.app.Fragment

-keepclasseswithmembernames class * {
    native <methods>;
}
 
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
 
-keepclasseswithmembernames class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
 
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
 
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}