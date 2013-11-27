/*
 *   Licensed to the Apache Software Foundation (ASF) under one
 *   or more contributor license agreements.  See the NOTICE file
 *   distributed with this work for additional information
 *   regarding copyright ownership.  The ASF licenses this file
 *   to you under the Apache License, Version 2.0 (the
 *   "License"); you may not use this file except in compliance
 *   with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 *
 */

package org.apache.directory.mavibot.btree.managed;


import java.io.IOException;


/**
 * A class containing utility methods to be used internally. 
 *
 * @author <a href="mailto:dev@directory.apache.org">Apache Directory Project</a>
 */
@SuppressWarnings("all")
/* No qualifier */class InternalUtil
{
    /**
     * Sets the multi-value container(a.k.a dupsContainer) of the key at the index below the given position( i.e pos - 1)
     * and resets the 'dupsPos' to the number of elements present in the multi-value container.
     * This is used by Cursor while navigating using prev() 
     *
     * @param parentPos the parent position object
     * @param btree the BTree
     */
    public static void changePrevDupsContainer( ParentPos parentPos, BTree btree ) throws IOException
    {
        if ( !btree.isAllowDuplicates() )
        {
            return;
        }

        if ( parentPos.pos >= 0 )
        {
            Leaf leaf = ( Leaf ) ( parentPos.page );
            ValueHolder valueHolder = leaf.values[parentPos.pos];
            parentPos.valueCursor = valueHolder.getCursor();
        }
    }


    /**
     * Same as @see #changePrevDupsContainer(ParentPos, BTree) but with a different name
     * to make it sound semantically right when used inside {@link BTreeFactory#getPathToRightMostLeaf(BTree)} 
     */
    public static void setLastDupsContainer( ParentPos parentPos, BTree btree ) throws IOException
    {
        changePrevDupsContainer( parentPos, btree );
    }
}
