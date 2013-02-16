#include <jni.h>
#include <android/log.h>

namespace com_marakana_android_yamba_yambaservice {

// Plain C code
static void log(const char* msg) {
	__android_log_print(ANDROID_LOG_DEBUG, "YambaService", "msg=%s", msg);
}

// JNI-to-C wrapper
static void log_jni(JNIEnv *env, jclass clazz, jstring message) {
	const char *msg = env->GetStringUTFChars(message, 0);
	log(msg);
	env->ReleaseStringUTFChars(message, msg);
}

static JNINativeMethod method_table[] = {
		{ "log", "(Ljava/lang/String;)V",(void *) log_jni } };
}

using namespace com_marakana_android_yamba_yambaservice;

extern "C" jint JNI_OnLoad(JavaVM* vm, void* reserved) {
	JNIEnv* env;
	if (vm->GetEnv(reinterpret_cast<void**>(&env), JNI_VERSION_1_6) != JNI_OK) {
		return JNI_ERR;
	} else {
		jclass clazz = env->FindClass(
				"com/marakana/android/yamba/yambaservice/YambaLib");
		if (clazz) {
			jint ret = env->RegisterNatives(clazz, method_table,
					sizeof(method_table) / sizeof(method_table[0]));
			env->DeleteLocalRef(clazz);
			return ret == 0 ? JNI_VERSION_1_6 : JNI_ERR;
		} else {
			return JNI_ERR;
		}
	}
}

