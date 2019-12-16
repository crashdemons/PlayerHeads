/*
 *  This Source Code Form is subject to the terms of the Mozilla Public
 *  License, v. 2.0. If a copy of the MPL was not distributed with this
 *  file, You can obtain one at http://mozilla.org/MPL/2.0/ .
 */
package com.github.crashdemons.playerheads.api.extensions;

import com.github.crashdemons.playerheads.api.HeadRepresentation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Interface defining methods for identifying and configuring custom heads you are registering.
 * @author crashdemons (crashenator at gmail.com)
 * @deprecated Draft API - not implemented yet.
 */
@Deprecated
public interface HeadExtension {
    public  boolean isHandledHead(final HeadRepresentation hr);
    @NotNull public  double getDropRate(final CustomHead hr);
}
