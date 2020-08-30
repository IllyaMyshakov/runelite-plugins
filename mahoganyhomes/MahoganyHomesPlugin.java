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
package mahoganyhomes;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import lombok.Getter;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.TileObject;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.mahoganyhomes.contracts.ArdougneContract;
import net.runelite.client.plugins.mahoganyhomes.contracts.Contract;
import net.runelite.client.plugins.mahoganyhomes.contracts.FaladorContract;
import net.runelite.client.plugins.mahoganyhomes.contracts.HosidiusContract;
import net.runelite.client.plugins.mahoganyhomes.contracts.VarrockContract;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.ui.overlay.worldmap.WorldMapPointManager;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.Text;

@PluginDescriptor(
	name = "Mahogany Homes",
	description = "Show helpful information for Mahogany Homes construction contracts",
	tags = {"construction", "minigame", "highlight", "overlay"}
)
public class MahoganyHomesPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private MahoganyHomesOverlay mahoganyHomesOverlay;

	@Inject
	private MahoganyHomesWorldOverlay mahoganyHomesWorldOverlay;

	@Inject
	private WorldMapPointManager worldMapPointManager;

	@Getter
	private final List<TileObject> objectsToMark = new ArrayList<>();

	@Inject
	@Getter
	private Client client;

	private Contract currentContract = null;
	private BufferedImage mapArrow;
	private BufferedImage constructionIcon;

	@Override
	protected void startUp() throws Exception
	{
		overlayManager.add(mahoganyHomesOverlay);
		overlayManager.add(mahoganyHomesWorldOverlay);
	}

	@Override
	protected void shutDown() throws Exception
	{
		overlayManager.remove(mahoganyHomesOverlay);
		overlayManager.remove(mahoganyHomesWorldOverlay);
		worldMapPointManager.removeIf(MahoganyHomesWorldMapPoint.class::isInstance);
		currentContract = null;
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (event.getType() != ChatMessageType.GAMEMESSAGE)
		{
			return;
		}

		String text = Text.removeTags(event.getMessage());

		if (text.startsWith("Go see "))
		{
			currentContract = setContract(text);
			worldMapPointManager.add(new MahoganyHomesWorldMapPoint(currentContract.getLocation(), this));
		}
		else if (text.contains(" seems happy with your work. Talk to him for your reward") ||
			text.contains(" seems happy with your work. Talk to her for your reward"))
		{
			worldMapPointManager.removeIf(MahoganyHomesWorldMapPoint.class::isInstance);
			currentContract = null;
		}

	}

	@Subscribe
	public void onGameObjectSpawned(GameObjectSpawned event)
	{
		GameObject gameObject = event.getGameObject();

	}

	@Subscribe
	public void onGameObjectChanged(GameObjectChanged event)
	{
		GameObject gameObject = event.getGameObject();
	}

	@Subscribe
	public void onGameObjectDespawned(GameObjectDespawned event)
	{
		GameObject gameObject = event.getGameObject();
	}

	public BufferedImage getMapArrow()
	{
		if (mapArrow != null)
		{
			return mapArrow;
		}

		mapArrow = ImageUtil.getResourceStreamFromClass(getClass(), "/util/clue_arrow.png");

		return mapArrow;
	}

	public BufferedImage getConstructionImage()
	{
		if (constructionIcon != null)
		{
			return constructionIcon;
		}

		constructionIcon = ImageUtil.getResourceStreamFromClass(getClass(), "/skill_icons/construction.png");

		return constructionIcon;
	}

	public Contract getContract()
	{
		return currentContract;
	}

	private Contract setContract(String text)
	{
		if (text.isEmpty())
		{
			return null;
		}

		Contract ret = null;

		for (String npc : Contract.getNpcNames())
		{
			if (text.contains(npc))
			{
				int npcId = Contract.getNpcID(npc);
				Contract.Region region = Contract.getRegion(npcId);
				// Check if Ardougne contracts have this NPC
				if (region == Contract.Region.ARDOUGNE)
				{
					ret = new ArdougneContract(npc, npcId);
				}
				// Check if Falador contracts have this NPC
				if (region == Contract.Region.FALADOR)
				{
					ret = new FaladorContract(npc, npcId);
				}
				// Check if Hosidius contracts have this NPC
				if (region == Contract.Region.HOSIDIUS)
				{
					ret = new HosidiusContract(npc, npcId);
				}
				// Check if Varrock contracts have this NPC
				if (region == Contract.Region.VARROCK)
				{
					ret = new VarrockContract(npc, npcId);
				}
				break;
			}
		}
		return ret;
	}
}
