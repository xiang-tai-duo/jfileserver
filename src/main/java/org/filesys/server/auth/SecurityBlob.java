/*
 * Copyright (C) 2018 GK Spencer
 *
 * JFileServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JFileServer is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with JFileServer. If not, see <http://www.gnu.org/licenses/>.
 */

package org.filesys.server.auth;

import org.filesys.server.auth.ntlm.NTLM;

/**
 * Security Blob class
 *
 * <p>Holds the details of a security blob received from a client, and the response blob generated by the server
 * for multi-stage logons</p>
 *
 * @author gkspencer
 */
public class SecurityBlob {

    // Security blob types
    public enum SecurityBlobType {
        NTLMSSP,
        SPNEGO;
    }

    // Security blob details
    private byte[] m_secBlob;
    private int m_secOffset;
    private int m_secLen;

    private SecurityBlobType m_secType;

    private boolean m_secUnicode;

    // Response security blob details
    private byte[] m_respBlob;

    /**
     * Class constructor
     *
     * @param secType SecurityBlobType
     * @param secBuf byte[]
     * @param unicode boolean
     */
    public SecurityBlob( SecurityBlobType secType, byte[] secBuf, boolean unicode) {
        m_secType = secType;
        m_secUnicode = unicode;

        m_secBlob = secBuf;
        m_secOffset = 0;
        m_secLen = m_secBlob.length;
    }

    /**
     * Class constructor
     *
     * @param secType SecurityBlobType
     * @param secBuf byte[]
     * @param offset int
     * @param len int
     * @param unicode boolean
     */
    public SecurityBlob( SecurityBlobType secType, byte[] secBuf, int offset, int len, boolean unicode) {
        m_secType = secType;
        m_secUnicode = unicode;

        m_secBlob = secBuf;
        m_secOffset = offset;
        m_secLen = len;
    }

    /**
     * Return the security blob type
     *
     * @return SecurityBlobType
     */
    public final SecurityBlobType isType() {
        return m_secType;
    }

    /**
     * Check if the security blob is an NTLMSSP format blob
     *
     * @return boolean
     */
    public final boolean isNTLMSSP() {
        return isType() == SecurityBlobType.NTLMSSP;
    }

    /**
     * Check if the security blob is an SPNEGO format blob
     *
     * @return boolean
     */
    public final boolean isSPNEGO() {
        return isType() == SecurityBlobType.SPNEGO;
    }

    /**
     * Check if the security blob is using Unicode strings
     *
     * @return boolean
     */
    public final boolean isUnicode() {
        return m_secUnicode;
    }

    /**
     * Return the security blob buffer
     *
     * @return byte[]
     */
    public final byte[] getSecurityBlob() {
        return m_secBlob;
    }

    /**
     * Return the offset of the security blob within the buffer
     *
     * @return int
     */
    public final int getSecurityOffset() {
        return m_secOffset;
    }

    /**
     * Return the length of the security blob
     *
     * @return int
     */
    public final int getSecurityLength() {
        return m_secLen;
    }

    /**
     * Check if there is a valid response buffer
     *
     * @return boolean
     */
    public final boolean hasResponseBlob() {
        return m_respBlob != null ? true : false;
    }

    /**
     * Return the response blob
     *
     * @return byte[]
     */
    public final byte[] getResponseBlob() {
        return m_respBlob;
    }

    /**
     * Return the response blob length, or zero if there is no response blob
     *
     * @return int
     */
    public final int getResponseLength() {
        return m_respBlob != null ? m_respBlob.length : 0;
    }

    /**
     * Set the response blob
     *
     * @param respBlob byte[]
     */
    public final void setResponseBlob( byte[] respBlob) {
        m_respBlob = respBlob;
    }

    /**
     * Check if a security blob is an NTLMSSP type security blob
     *
     * @param secBuf byte[]
     * @param offset int
     * @return boolean
     */
    public static final boolean checkForNTLMSSP(byte[] secBuf, int offset) {

        for ( int idx = 0; idx < NTLM.Signature.length; idx++) {
            if ( secBuf[idx + offset] != NTLM.Signature[ idx])
                return false;
        }

        return true;
    }

    /**
     * Return the security blob details as a string
     *
     * @return String
     */
    public final String toString() {

        StringBuffer str = new StringBuffer();

        str.append("[");
        str.append( isType().name());
        str.append(" secBlob=[offset=");
        str.append(getSecurityOffset());
        str.append(",len=");
        str.append(getSecurityLength());
        str.append("]");

        if ( isUnicode())
            str.append(" Unicode");

        str.append(", respBlob=");

        if ( hasResponseBlob()) {
            str.append(getResponseBlob().length);
            str.append("bytes");
        }
        else
            str.append("null");

        str.append("]");

        return str.toString();
    }
}
