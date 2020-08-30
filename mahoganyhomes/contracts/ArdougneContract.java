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

public class ArdougneContract extends Contract
{
	private static final HashSet<Integer> JESS_FURNITURE_IDS = new HashSet<Integer>()
	{
		{
			// Drawers to Remove: 40187, Build Space: 40082
			add(ObjectID.DRAWERS_40187);
			add(ObjectID.DRAWER_SPACE);
			// Cabinets to Remove: 39899, 39905, Build Space: 40073
			add(ObjectID.CABINET_39899);
			add(ObjectID.CABINET_39905);
			add(ObjectID.CABINET_SPACE);
			// Bed to Remove: 40193, Build Space: 40080
			add(ObjectID.BED_40193);
			add(ObjectID.BED_SPACE_40080);
			// Table to Remove: 40108, Build Space: 40070
			add(ObjectID.TABLE_40108);
			add(ObjectID.TABLE_SPACE_40070);
			// Broken Grandfather Clock: 40141
			add(ObjectID.GRANDFATHER_CLOCK_40141);
			// Bath
			add(ObjectID.BROKEN_BATH);
		}
	};

	private static final HashSet<Integer> NOELLA_FURNITURE_IDS = new HashSet<Integer>()
	{
		{
			// Dresser to Remove: 33948, Build Space: 40076
			add(ObjectID.DRESSER_39948);
			add(ObjectID.DRESSER_SPACE_40076);
			// Cupboard to Remove: 40115, Build Space: 40078
			add(ObjectID.CUPBOARD_40115);
			add(ObjectID.CUPBOARD_SPACE);
			// Broken Hat stand: 40139
			add(ObjectID.HAT_STAND_40139);
			// Broken Mirror: 40179
			add(ObjectID.BROKEN_MIRROR_40179);
			// Drawers to Remove: 40181, Build Space: 40082
			add(ObjectID.DRAWERS_40181);
			add(ObjectID.DRAWER_SPACE);
			// Table to Remove: 40108, Build Space: 40070
			add(ObjectID.TABLE_40108);
			add(ObjectID.TABLE_SPACE_40070);
			// Broken Grandfather Clock: 40141
			add(ObjectID.GRANDFATHER_CLOCK_40141);
		}
	};

	private static final HashSet<Integer> ROSS_FURNITURE_IDS = new HashSet<Integer>()
	{
		{
			// Broken Range: 40148
			add(ObjectID.BROKEN_RANGE_40148);
			// Drawers to Remove: 40187, Build Space: 40082a
			add(ObjectID.DRAWERS_40187);
			// Large Bed to Remove: 40200, Build Space: 40080
			add(ObjectID.BED_40200);
			add(ObjectID.BED_SPACE_40080);
			// Broken Hat Stand: 40139
			add(ObjectID.HAT_STAND_40139);
			// Bed to Remove: 40207, Build Space: 40081
			add(ObjectID.BED_40207);
			add(ObjectID.BED_SPACE_40081);
			// Broken Mirror: 40179
			add(ObjectID.BROKEN_MIRROR_40179);
		}
	};

	// Furniture mapping for World Overlay
	private static final HashMap<Integer, HashSet<Integer>> npcFurnitureMap = new HashMap<Integer, HashSet<Integer>>()
	{
		{
			put(NpcID.JESS, JESS_FURNITURE_IDS);
			put(NpcID.NOELLA, NOELLA_FURNITURE_IDS);
			put(NpcID.ROSS, ROSS_FURNITURE_IDS);
		}
	};

	// Coordinate mapping for World Map Points
	public static final HashMap<Integer, WorldPoint> npcCoordinateMap = new HashMap<Integer, WorldPoint>()
	{
		{
			put(NpcID.JESS, new WorldPoint(2622, 3298, 0));
			put(NpcID.NOELLA, new WorldPoint(2659, 3328, 0));
			put(NpcID.ROSS, new WorldPoint(2614, 3322, 0));
		}
	};

	// Location mapping for Overlay
	private final HashMap<Integer, String> npcOverlayMap = new HashMap<Integer, String>()
	{
		{
			put(NpcID.JESS, "Floor above pet insurance shop");
			put(NpcID.NOELLA, "North-west of market");
			put(NpcID.ROSS, "North of church");
		}
	};

	public ArdougneContract(String npc, Integer npcId)
	{
		super(npc, npcId, Region.ARDOUGNE, npcCoordinateMap.get(npcId));
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
