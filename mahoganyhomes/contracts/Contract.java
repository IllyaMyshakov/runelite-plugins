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

import com.google.common.collect.ImmutableList;
import java.util.HashMap;
import java.util.HashSet;
import lombok.Getter;
import net.runelite.api.NpcID;
import net.runelite.api.coords.WorldPoint;
import static net.runelite.client.plugins.cluescrolls.ClueScrollOverlay.TITLED_CONTENT_COLOR;
import net.runelite.client.plugins.mahoganyhomes.MahoganyHomesPlugin;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

@Getter
public abstract class Contract
{
	public enum Region
	{
		ARDOUGNE, FALADOR, HOSIDIUS, VARROCK
	}

	private static final ImmutableList<String> NPC_NAMES = ImmutableList.of(
		// Ardougne
		"Jess", "Noella", "Ross",
		// Falador
		"Larry", "Norman", "Tau",
		// Hosidius
		"Barbara", "Leela", "Mariah",
		// Varrock
		"Bob", "Jeff", "Sarah"
	);

	public static final HashMap<String, Integer> NPC_IDS = new HashMap<String, Integer>()
	{
		{
			// Ardougne
			put("Jess", NpcID.JESS);
			put("Noella", NpcID.NOELLA);
			put("Ross", NpcID.ROSS);
			// Falador
			put("Larry", NpcID.LARRY_10418);
			put("Norman", NpcID.NORMAN);
			put("Tau", NpcID.TAU);
			// Hosidius
			put("Barbara", NpcID.BARBARA);
			put("Leela", NpcID.LEELA_10423);
			put("Mariah", NpcID.MARIAH);
			// Varrock
			put("Bob", NpcID.BOB_10414);
			put("Jeff", NpcID.JEFF_10415);
			put("Sarah", NpcID.SARAH_10416);
		}
	};

	private static final HashMap<Integer, Region> NPC_LOCATIONS = new HashMap<Integer, Region>()
	{
		{
			// Ardougne
			put(NpcID.JESS, Region.ARDOUGNE);
			put(NpcID.NOELLA, Region.ARDOUGNE);
			put(NpcID.ROSS, Region.ARDOUGNE);
			// Falador
			put(NpcID.LARRY_10418, Region.FALADOR);
			put(NpcID.NORMAN, Region.FALADOR);
			put(NpcID.TAU, Region.FALADOR);
			// Hosidius
			put(NpcID.BARBARA, Region.HOSIDIUS);
			put(NpcID.LEELA_10423, Region.HOSIDIUS);
			put(NpcID.MARIAH, Region.HOSIDIUS);
			// Varrock
			put(NpcID.BOB_10414, Region.VARROCK);
			put(NpcID.JEFF_10415, Region.VARROCK);
			put(NpcID.SARAH_10416, Region.VARROCK);
		}
	};

	private final String npcName;
	private final Integer npcId;
	private final Region region;
	private final WorldPoint location;

	public Contract(String npcName, Integer npcId, Region region, WorldPoint location)
	{
		this.npcName = npcName;
		this.npcId = npcId;
		this.region = region;
		this.location = location;
	}

	public void makeOverlay(PanelComponent panelComponent, MahoganyHomesPlugin plugin)
	{
		panelComponent.getChildren().add(TitleComponent.builder().text("Current Contract").build());
		panelComponent.getChildren().add(LineComponent.builder().left("NPC:").build());
		panelComponent.getChildren().add(LineComponent.builder()
			.left(npcName)
			.leftColor(TITLED_CONTENT_COLOR)
			.build());

		panelComponent.getChildren().add(LineComponent.builder().left("Location:").build());
		panelComponent.getChildren().add(LineComponent.builder()
			.left(getHint() + ", " + getRegionString(region))
			.leftColor(TITLED_CONTENT_COLOR)
			.build());
	}

	public static Integer getNpcID(String npc)
	{
		return NPC_IDS.get(npc);
	}

	public static ImmutableList<String> getNpcNames()
	{
		return NPC_NAMES;
	}

	public abstract String getHint();

	public abstract HashSet<Integer> getFurniture();

	public static Region getRegion(Integer npcId)
	{
		return NPC_LOCATIONS.get(npcId);
	}

	private static String getRegionString(Region assignedRegion)
	{
		if (assignedRegion == Region.ARDOUGNE)
		{
			return "Ardougne";
		}
		if (assignedRegion == Region.FALADOR)
		{
			return "Falador";
		}
		if (assignedRegion == Region.HOSIDIUS)
		{
			return "Hosidius";
		}
		if (assignedRegion == Region.VARROCK)
		{
			return "Varrock";
		}

		return "";
	}
}
