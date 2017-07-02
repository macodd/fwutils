import { Delay } from './delay';
import { Pipe, PipeTransform } from '@angular/core';

const MINUTE = 60;
const HOUR = 60 * MINUTE;
const DAY = 24 * HOUR;

const FORMAT_DETAIL_LAYOUT_SIMPLE = 'simple';
const FORMAT_DETAIL_LAYOUT_DETAIL = 'detail';

@Pipe({ name: 'formatDelay' })
export class FormatDelayPipe implements PipeTransform {

  static numberFixedLen(input: number, resultLength: number): string {

    let result = '' + input;
    while (result.length < resultLength) {
      result = '0' + result;
    }
    return result;
  };

  transform(delay: Delay, format: string): string {

    if (!delay) {
      return '';
    }

    let usedFormat = null;
    if (format) {
      if (format.toLowerCase() === FORMAT_DETAIL_LAYOUT_SIMPLE) {
        usedFormat = FORMAT_DETAIL_LAYOUT_SIMPLE;
      } else if (format.toLowerCase() === FORMAT_DETAIL_LAYOUT_DETAIL) {
        usedFormat = FORMAT_DETAIL_LAYOUT_DETAIL;
      }
    }


    let result = '';
    if (usedFormat === FORMAT_DETAIL_LAYOUT_DETAIL) {
      const days = delay.days ? delay.days : 0;
      const hours = delay.hours ? delay.hours : 0;
      const minutes = delay.minutes ? delay.minutes : 0;

      result = days + ' Tage ' + hours + ' Stunden ' + minutes + ' Minuten';
    } else {
      // Default
      const days = FormatDelayPipe.numberFixedLen(delay.days ? delay.days : 0, 2);
      const hours = FormatDelayPipe.numberFixedLen(delay.hours ? delay.hours : 0, 2);
      const minutes = FormatDelayPipe.numberFixedLen(delay.minutes ? delay.minutes : 0, 2);

      result = days + ':' + hours + ':' + minutes;
    }

    return result;
  }
}
