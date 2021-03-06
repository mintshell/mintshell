/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class org_mintshell_terminal_ncurses_interfaces_NCursesTerminal */

#ifndef _Included_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
#define _Included_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    addCh
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_addCh
  (JNIEnv *, jclass, jint);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    beep
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_beep
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    clear
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_clear
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    delCh
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_delCh
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    endwin
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_endwin
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    getCh
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getCh
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    getMaxX
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getMaxX
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    getMaxY
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getMaxY
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    getX
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getX
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    getY
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getY
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    hasColors
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_hasColors
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    init
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_init
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    move
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_move
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    mvDelCh
 * Signature: (II)V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_mvDelCh
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    refresh
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_refresh
  (JNIEnv *, jclass);

/*
 * Class:     org_mintshell_terminal_ncurses_interfaces_NCursesTerminal
 * Method:    scroll
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_scroll
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
