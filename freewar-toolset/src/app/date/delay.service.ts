import { Injectable } from '@angular/core';
import { Delay } from './delay';

const MINUTE = 60;
const HOUR = 60 * MINUTE;
const DAY = 24 * HOUR;

@Injectable()
export class DelayService {

  convertToDelay(secondsInput: number): Delay {
    let remainingSeconds = secondsInput;
    const result: Delay = { seconds: 0 };

    if (remainingSeconds > DAY) {
      const days = Math.floor(remainingSeconds / DAY);
      remainingSeconds -= days * DAY;
      result.days = days;
    }

    if (remainingSeconds > HOUR) {
      const hours = Math.floor(remainingSeconds / HOUR);
      remainingSeconds -= hours * HOUR;
      result.hours = hours;
    }

    if (remainingSeconds > MINUTE) {
      const minutes = Math.floor(remainingSeconds / MINUTE);
      remainingSeconds -= minutes * MINUTE;
      result.minutes = minutes;
    }

    if (remainingSeconds > 0) {
      const seconds = Math.floor(remainingSeconds);
      result.seconds = seconds;
    }

    return result;

  }
}

