# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
#QuickLogin
-keep class com.energysh.quicklogin.**{*;}
-keep interface com.energysh.quicklogin.** {*;}
-keep enum com.energysh.quicklogin.** {*;}
-keep class com.cmic.sso.sdk.**{*;}
-keep interface com.cmic.sso.sdk.** {*;}
-keep enum com.cmic.sso.sdk.** {*;}
-keepattributes *Annotation*
-keepattributes *JavascriptInterface*
-dontwarn cn.com.chinatelecom.account.lib.**
-keepclassmembers class cn.com.chinatelecom.account.lib.**{*;}
-keep class cn.com.chinatelecom.account.lib.**{*;}