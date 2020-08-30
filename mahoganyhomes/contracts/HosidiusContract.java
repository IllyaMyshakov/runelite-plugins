/*
 * Copyright (c) 2020, Illya Myshakov <https://github.com/IllyaMyshakov>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package mahoganyhomes.contracts;

import java.util.HashMap;
import java.util.HashSet;
import net.runelite.api.NpcID;
import net.runelite.api.ObjectID;
import net.runelite.api.coords.WorldPoint;

public class HosidiusContract extends Contract
{
	private static final HashSet<Integer> BARBARA_FURNITURE_IDS = new HashSet<Integer>()
	{
		{
			// Broken Grandfather Clock: 39897
			add(ObjectID.BROKEN_GRANDFATHER_CLOCK);
			// Table to Remove: 40017, Build Space: 40072
			add(ObjectID.TABLE_40017);
			add(ObjectID.TABLE_SPACE_40072);
			// Bed to Remove: 40043, Build Space: 40081
			add(ObjectID.BED_40043);
			add(ObjectID.BED_SPACE_40081);
			// Chair to Remove: 40062, Build Space: 40079
			add(ObjectID.CHAIR_40062);
			add(ObjectID.CHAIR_SPACE_40079);
			// Drawers to Remove: 40056, Build Space: 40082
			add(ObjectID.DRAWERS_40056);
			add(ObjectID.DRAWER_SPACE);
			// Broken Range: 40067
			add(ObjectID.BROKEN_RANGE_40067);
		}
	};
	private static final HashSet<Integer> LEELA_FURNITURE_IDS = new HashSet<Integer>()
	{
		{
			// Table to Removes: 40017, 40031, Build Spaces: 40069, 40072
			add(ObjectID.TABLE_40017);
			add(ObjectID.TABLE_40031);
			add(ObjectID.TABLE_SPACE_40069);
			add(ObjectID.TABLE_SPACE_40072);
			// Broken Mirror: 40179
			add(ObjectID.BROKEN_MIRROR_40179);
			// Broken Sink: 40022
			add(ObjectID.BROKEN_SINK);
			// Bed to Remove: 40050, Build Space: 40080
			add(ObjectID.BED_40050);
			add(ObjectID.BED_SPACE_40080);
			// Cupboard to Remove: 40115, Build Space: 40078
			add(ObjectID.CUPBOARD_40115);
			add(ObjectID.CUPBOARD_SPACE);
		}
	};
	private static final HashSet<Integer> MARIAH_FURNITURE_IDS = new HashSet<Integer>()
	{
		{
			// Tables to Remove: 40017, 40031, Build Space: 40069, 40072
			add(ObjectID.TABLE_40017);
			add(ObjectID.TABLE_40031);
			add(ObjectID.TABLE_SPACE_40069);
			add(ObjectID.TABLE_SPACE_40072);
			// Shelves to Remove: 40025, Build Space: 40077
			add(ObjectID.COOKING_SHELVES_40025);
			add(ObjectID.SHELVES_SPACE);
			// Bed to Remove: 40043, Build Space: 40081
			add(ObjectID.BED_40043);
			add(ObjectID.BED_SPACE_40081);
			// Broken Sink: 40022
			add(ObjectID.BROKEN_SINK);
			// Cupboard to Remove: 40037, Build Space: 40078
			add(ObjectID.CUPBOARD_40037);
			add(ObjectID.CUPBOARD_SPACE);
			// Broken Hat Stand: 40139
			add(ObjectID.HAT_STAND_40139);
		}
	};
	// Furniture mapping for World Overlay
	private static final HashMap<Integer, HashSet<Integer>> npcFurnitureMap = new HashMap<Integer, HashSet<Integer>>()
	{
		{
			put(NpcID.BARBARA, BARBARA_FURNITURE_IDS);
			put(NpcID.LEELA_10423, LEELA_FURNITURE_IDS);
			put(NpcID.MARIAH, MARIAH_FURNITURE_IDS);
		}
	};

	// Coordinate mapping for World Map Points
	public static final HashMap<Integer, WorldPoint> npcCoordinateMap = new HashMap<Integer, WorldPoint>()
	{
		{
			put(NpcID.BARBARA, new WorldPoint(1750, 3540, 0));
			put(NpcID.LEELA_10423, new WorldPoint(1785, 3598, 0));
			put(NpcID.MARIAH, new WorldPoint(1766, 3627, 0));
		}
	};

	// Location map for Overlay
	public static final HashMap<Integer, String> npcOverlayMap = new HashMap<Integer, String>()
	{
		{
			put(NpcID.BARBARA, "East of wind mill");
			put(NpcID.LEELA_10423, "East of town market");
			put(NpcID.MARIAH, "South-west of estate agent");
		}
	};

	public HosidiusContract(String npc, Integer npcId)
	{
		super(npc, npcId, Region.HOSIDIUS, npcCoordinateMap.get(npcId));
	}

	@Override
	public String getHint()
	{
		return npcOverlayMap.get(getNpcId());
	}

	@Override
	public HashSet<Integer> getFurniture()
	{
		return npcFurnitureMap.get(getNpcId());
	}
}
