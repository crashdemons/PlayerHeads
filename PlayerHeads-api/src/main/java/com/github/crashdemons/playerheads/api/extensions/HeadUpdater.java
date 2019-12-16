/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import com.github.crashdemons.playerheads.api.HeadRepresentation;
import org.jetbrains.annotations.Nullable;

/**
 * Interface defining a class that updates detected heads based on custom logic.
 * Warning: The update method is called for every head detected, so registering an updater may impact performance.
 * @author crashdemons (crashenator at gmail.com)
 * @deprecated draft api - not fully implemented
 */
@Deprecated
public interface HeadUpdater {
    @Nullable public  HeadRepresentation updateHead(final HeadRepresentation hr);
}
