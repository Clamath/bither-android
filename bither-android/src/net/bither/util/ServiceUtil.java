/*
 * Copyright 2014 http://Bither.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.bither.util;

import java.util.Date;

import net.bither.BitherApplication;
import net.bither.service.BlockchainService;
import android.content.Intent;

public class ServiceUtil {
	private ServiceUtil() {

	};

	public static void doMarkTimerTask(boolean isRunning) {
		Intent intent = new Intent(BlockchainService.ACTION__TIMER_TASK_STAT,
				null, BitherApplication.mContext, BlockchainService.class);
		intent.putExtra(BlockchainService.ACTION_TIMER_TASK_ISRUNNING,
				isRunning);
		BitherApplication.mContext.startService(intent);
	}

	public static void dowloadSpvBlock() {
		Intent intent = new Intent(
				BlockchainService.ACTION_BEGIN_DOWLOAD_SPV_BLOCK, null,
				BitherApplication.mContext, BlockchainService.class);
		BitherApplication.mContext.startService(intent);
	}

	private static boolean getIsNoSoundWithoutWeekend(int hour) {
		return hour == 23 || hour < 8;
	}

	public static boolean isNoPrompt(long timeMillis) {
		int week = DateTimeUtil.getDayOfWeek(timeMillis) - 1;

		int hour = DateTimeUtil.getHour(timeMillis);
		boolean isNoSound = false;
		switch (week) {
		case 0:
			isNoSound = (hour >= 1 && hour < 10) || (hour == 23);
			break;
		case 5:
			isNoSound = hour < 8;
			break;
		case 6:
			isNoSound = hour >= 1 && hour < 10;
			break;
		default:
			isNoSound = getIsNoSoundWithoutWeekend(hour);
			break;
		}
		if (isNoSound) {
			LogUtil.d(
					"NoSound",
					String.format("week:%d,hour:%d", week, hour)
							+ "  time:"
							+ DateTimeUtil.getDateTimeString(new Date(
									timeMillis)));
			LogUtil.d("NoSound", "result:" + Boolean.toString(isNoSound));

		} else {
			LogUtil.d(
					"Sound",
					String.format("week:%d,hour:%d", week, hour)
							+ "  time:"
							+ DateTimeUtil.getDateTimeString(new Date(
									timeMillis)));
			LogUtil.d("Sound", "result:" + Boolean.toString(isNoSound));
		}
		return isNoSound;
	}

}
