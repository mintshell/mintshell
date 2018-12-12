/*
 * Copyright © 2017-2019 mintshell.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
package org.mintshell.terminal.ssh.interfaces;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.mintshell.assertion.Assert;

/**
 * The session registry managages all currently active {@link SshCommandInterfaceSession}s.
 *
 * @author Noqmar
 * @since 0.1.0
 */
class SessionRegistry {

  private final Set<SshCommandInterfaceSession> sessions;

  /**
   * Creates a new instance.
   *
   * @author Noqmar
   * @since 0.1.0
   */
  SessionRegistry() {
    this.sessions = new CopyOnWriteArraySet<>();
  }

  /**
   * Returns a {@link Set} of all current {@link SshCommandInterfaceSession}s.
   *
   * @return current {@link SshCommandInterfaceSession}s
   *
   * @author Noqmar
   * @since 0.1.0
   */
  Set<SshCommandInterfaceSession> getSessions() {
    return new HashSet<>(this.sessions);
  }

  /**
   * Registers the given {@link SshCommandInterfaceSession} which is afterwards available through
   * {@link #getSessions()}.
   *
   * @param session
   *          session to register
   *
   * @author Noqmar
   * @since 0.1.0
   */
  void register(final SshCommandInterfaceSession session) {
    this.sessions.add(Assert.ARG.isNotNull(session, "[session] must not be [null]"));
  }

  /**
   * Unregistersthe given {@link SshCommandInterfaceSession} which is afterwards not longer available through
   * {@link #getSessions()}.
   * 
   * @param session
   *          session to unregister
   *
   * @author Noqmar
   * @since 0.1.0
   */
  void unregister(final SshCommandInterfaceSession session) {
    this.sessions.remove(Assert.ARG.isNotNull(session, "[session] must not be [null]"));
  }
}
