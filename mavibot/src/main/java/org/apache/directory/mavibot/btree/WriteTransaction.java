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
package org.apache.directory.mavibot.btree;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.directory.mavibot.btree.exception.BadTransactionStateException;

/**
 * A data structure used to manage a write transaction
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
/* no qualifier */ class WriteTransaction
{
    /** The recordManager on which this transaction is applied */
    private RecordManager recordManager;

    /** A lock used to protect the write operation against concurrent access */
    protected ReentrantLock writeLock;

    /* no qualifier */WriteTransaction( RecordManager recordManager )
    {
        System.out.println( "Creating the transaction oject" );
        this.recordManager = recordManager;
        writeLock = new ReentrantLock();
    }


    /* no qualifier */ void start()
    {
        if ( writeLock.isLocked() )
        {
            throw new BadTransactionStateException( "Cannot start a write transaction when it's already started" );
        }

        writeLock.lock();
    }


    /* no qualifier */ void commit()
    {
        if ( !writeLock.isLocked() )
        {
            throw new BadTransactionStateException( "Cannot commit a write transaction when it's not started" );
        }

        writeLock.unlock();
    }


    /* no qualifier */ void rollback()
    {
        if ( !writeLock.isLocked() )
        {
            throw new BadTransactionStateException( "Cannot commit a write transaction when it's not started" );
        }

        writeLock.unlock();
    }
}
