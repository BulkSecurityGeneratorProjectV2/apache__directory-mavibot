/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package org.apache.mavibot.btree.serializer;


import java.io.IOException;
import java.util.Comparator;

import org.apache.mavibot.btree.comparator.ByteArrayComparator;


/**
 * A serializer for a byte[].
 * 
 * @author <a href="mailto:labs@labs.apache.org">Mavibot labs Project</a>
 */
public class ByteArraySerializer implements ElementSerializer<byte[]>
{
    /** The associated comparator */
    private final Comparator<byte[]> comparator;


    /**
     * Create a new instance of ByteArraySerializer
     */
    public ByteArraySerializer()
    {
        comparator = new ByteArrayComparator();
    }


    /**
     * {@inheritDoc}
     */
    public byte[] serialize( byte[] element )
    {
        int len = -1;

        if ( element != null )
        {
            len = element.length;
        }

        byte[] bytes = null;

        switch ( len )
        {
            case 0:
                bytes = new byte[4];

                bytes[0] = 0x00;
                bytes[1] = 0x00;
                bytes[2] = 0x00;
                bytes[3] = 0x00;

                break;

            case -1:
                bytes = new byte[4];

                bytes[0] = ( byte ) 0xFF;
                bytes[1] = ( byte ) 0xFF;
                bytes[2] = ( byte ) 0xFF;
                bytes[3] = ( byte ) 0xFF;

                break;

            default:
                bytes = new byte[len + 4];

                System.arraycopy( element, 0, bytes, 4, len );

                bytes[0] = ( byte ) ( len >>> 24 );
                bytes[1] = ( byte ) ( len >>> 16 );
                bytes[2] = ( byte ) ( len >>> 8 );
                bytes[3] = ( byte ) ( len );
        }

        return bytes;
    }


    /**
     * {@inheritDoc}
     */
    public byte[] deserialize( BufferHandler bufferHandler ) throws IOException
    {
        byte[] in = bufferHandler.read( 4 );

        int len = IntSerializer.deserialize( in );

        switch ( len )
        {
            case 0:
                return new byte[]
                    {};

            case -1:
                return null;

            default:
                in = bufferHandler.read( len );

                return in;
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public int compare( byte[] type1, byte[] type2 )
    {
        if ( type1 == type2 )
        {
            return 0;
        }

        if ( type1 == null )
        {
            if ( type2 == null )
            {
                return 0;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            if ( type2 == null )
            {
                return 1;
            }
            else
            {
                if ( type1.length < type2.length )
                {
                    int pos = 0;

                    for ( byte b1 : type1 )
                    {
                        byte b2 = type2[pos];

                        if ( b1 == b2 )
                        {
                            pos++;
                        }
                        else if ( b1 < b2 )
                        {
                            return -1;
                        }
                        else
                        {
                            return 1;
                        }
                    }

                    return 1;
                }
                else
                {
                    int pos = 0;

                    for ( byte b2 : type2 )
                    {
                        byte b1 = type1[pos];

                        if ( b1 == b2 )
                        {
                            pos++;
                        }
                        else if ( b1 < b2 )
                        {
                            return -1;
                        }
                        else
                        {
                            return 1;
                        }
                    }

                    return -11;
                }
            }
        }
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Comparator<byte[]> getComparator()
    {
        return comparator;
    }
}
