#include "../../../jni/org_mintshell_terminal_ncurses_interfaces_NCursesTerminal.h"
#if (defined _PDCURSES_)
// Use the PDCurses version of the header file, which must be
// in the current directory.
#include "curses.h"
#elif (defined _USE_CURSES_)
#include <curses.h>
#include <term.h>
#elif (defined _USE_NCURSESW_)
#include <locale.h>
#include <ncursesw/curses.h>
#else
// Default is to use ncurses
#include <ncurses.h>
#endif
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <errno.h>

//************************************************************************
// LOCAL FUNCTION DECLARATIONS

//************************************************************************
// LOCAL VARIABLES
// Here are the boundaries of the clipping rectangle.
// Note that "right" refers to the righmost column INCLUDED in the rectangle
// and "bottom" refers to the bottom row that is INCLUDED in the rectangle.
static int left, top, right, bottom;

// This is a local copy of the cursor position, used for clipping.
static int cursorx = 0, cursory = 0;

static int hascolors = 0;	// set when we initialize
static int colors_started = 0;	// set when we call start_color().

//************************************************************************
// FUNCTION DEFINITIONS

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_addCh
(JNIEnv *env, jobject jo, jint chr_)
{
	addch(chr_);
}

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_beep
(JNIEnv *env, jobject jo)
{
	beep();
}

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_clear
(JNIEnv *env, jobject jo)
{
	clear();
}

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_delCh
(JNIEnv *env, jobject jo)
{
	delch();
}

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_endwin
(JNIEnv *env, jobject jo)
{
	endwin();
}

JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getCh(
		JNIEnv *env, jobject jo) {
	int utf[6] = { 0, 0, 0, 0, 0, 0 };
	utf[0] = getch();
	int c = 0;
	
	if (utf[0] == KEY_RESIZE) {
        	return KEY_RESIZE;
    }	

	if (utf[0] == -1)
		return -1;         // error

	if (utf[0] == 0631)
		return 0631;     // mouse event

	if ((utf[0] & 0x80) == 0) {
		//0xxxxxxxx (1 byte UTF-8)
		c = utf[0];
	} else if ((utf[0] & 0xe0) == 0xc0) {
		// 110xxxxx 10xxxxxx ( 2 byte UTF-8 )
		utf[1] = getch();
		if (utf[1] == -1)
			return -1;
		c = (utf[0] & 0x1f) * 0x40 + (utf[1] & 0x3f);
	} else if ((utf[0] & 0xf8) == 0xf0) {
		// 1110xxxx 10xxxxxx 10xxxxxx ( 3 byte UTF-8 )
		// cannot be tested = not supported :-(
		// must be similar to 2 byte encoding
	} else if ((utf[0] & 0xf0) == 0xf0) {
		// 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx( 4 byte UTF-8 )
		// cannot be tested = not supported :-(
		// must be similar to 2 byte encoding
	} else if ((utf[0] & 0xfc) == 0xf8) {
		// 111110xx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx( 5 byte UTF-8 )
		// cannot be tested = not supported :-(
		// must be similar to 2 byte encoding
	}
	if ((utf[0] & 0xfe) == 0xfc) {
		// 1111110x 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx 10xxxxxx ( 6 byte UTF-8 )
		// cannot be tested = not supported :-(
		// must be similar to 2 byte encoding
	}
	
	return c;
	//getch();
}

/**
 * Return the number of columns that the screen can display.
 * The ncurses does not return a reliable value for xterm windows,
 * where the screen size can change at runtime.
 */
JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getMaxX(
		JNIEnv *env, jobject jo) {
	int x, y;
	getmaxyx(stdscr, y, x);
	return (jint) x;
}

/**
 * Return the number of rows that the screen can display.
 */
JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getMaxY(
		JNIEnv *env, jobject jo) {
	int x, y;
	getmaxyx(stdscr, y, x);
	return (jint) y;
}

/**
 * Get the x position of the cursor.
 */
JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getX(
		JNIEnv *env, jobject jo) {
	int x, y;

	getyx(stdscr, y, x);
	return x;
}

/**
 * Get the y position of the cursor.
 */
JNIEXPORT jint JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_getY(
		JNIEnv *env, jobject jo) {
	int x, y;

	getyx(stdscr, y, x);
	return y;
}

/**
 * Returns true if the terminal is capable of displaying colors.
 */
JNIEXPORT jboolean JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_hasColors(
		JNIEnv *env, jobject jo) {
	return has_colors();
}

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_init
(JNIEnv *env, jobject jo)
{
	char *strcap;
	int i;

#if (defined _USE_NCURSESW_)
	// tested with "cs_CZ.UTF-8" setting, must work with any setting
	setlocale(LC_ALL,"");// from environment (LANG)
#endif

	initscr();
	keypad(stdscr, TRUE);   // enable keyboard mapping
	timeout(100);// wait up to 100msec for input
	noecho(); /* don't echo input */
	raw();
	nonl();
	scrollok(stdscr, TRUE);

	hascolors = has_colors();

	atexit((void (*)(void))endwin);
}

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_mvDelCh
(JNIEnv *env, jobject jo, jint x_, jint y_)
{
	mvdelch(y_, x_);
}

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_move
(JNIEnv *env, jobject jo, jint x_, jint y_)
{
	move(y_, x_);
}

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_refresh
(JNIEnv *env, jobject jo)
{
	refresh();
}

JNIEXPORT void JNICALL Java_org_mintshell_terminal_ncurses_interfaces_NCursesTerminal_scroll
(JNIEnv *env, jobject jo)
{
	scroll(stdscr);
}

