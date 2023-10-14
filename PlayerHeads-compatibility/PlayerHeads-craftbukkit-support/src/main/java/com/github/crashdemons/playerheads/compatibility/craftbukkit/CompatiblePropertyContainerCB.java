/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.compatibility.craftbukkit;

import com.mojang.authlib.properties.Property;
import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author crashdemons (crashenator at gmail.com)
 */
public class CompatiblePropertyContainerCB implements Iterable<CompatiblePropertyCB>{
    private final Collection<Property> props;
    public CompatiblePropertyContainerCB(Collection<Property> props){
        this.props = props;
    }
    
    public class CPCIterator implements Iterator<CompatiblePropertyCB>{

        private final Iterator<Property> iter;
        public CPCIterator(Iterator<Property> iter){
            this.iter=iter;
        }
        @Override
        public boolean hasNext() {
            return this.iter.hasNext();
        }

        @Override
        public CompatiblePropertyCB next() {
            return new CompatiblePropertyCB(this.iter.next());
        }
        
    }
    
    public Collection<Property> getBackingObject(){
        return this.props;
    }
    
    
    @Override
    public Iterator<CompatiblePropertyCB> iterator() {
        return new CPCIterator(this.props.iterator());
    }

    public int size() {
        return this.props.size();
    }

    public boolean isEmpty() {
        return this.props.isEmpty();
    }

    
}
