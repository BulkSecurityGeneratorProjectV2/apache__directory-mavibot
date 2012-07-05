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
package org.apache.mavibot.btree;

/**
 * The result of a delete operation, when the child has not been merged, and when
 * we have borrowed an element from the left sibling. It contains the
 * reference to the modified page, and the removed element.
 * 
 * @param <K> The type for the Key
 * @param <V> The type for the stored value

 * @author <a href="mailto:labs@laps.apache.org">Mavibot labs Project</a>
 */
/* No qualifier */ class BorrowedFromLeftResult<K, V> extends AbstractBorrowedFromSiblingResult<K, V>
{
    /**
     * The default constructor for BorrowedFromLeftResult.
     * 
     * @param modifiedPage The modified page
     * @param modifiedSibling The modified sibling
     * @param removedElement The removed element (can be null if the key wasn't present in the tree)
     * @param newLeftMost The element on the left of he current page
     */
    /* No qualifier */ BorrowedFromLeftResult( Page<K, V> modifiedPage, Page<K, V> modifiedSibling, Tuple<K, V> removedElement, K newLeftMost )
    {
        super( modifiedPage, modifiedSibling, removedElement, newLeftMost, AbstractBorrowedFromSiblingResult.SiblingPosition.LEFT );
    }
    
    
    /**
     * @see Object#toString()
     */
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append( "Borrowed from left" );
        sb.append( super.toString() );

        return sb.toString();
    }
}