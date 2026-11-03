// yes-no.pipe.ts
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'yesNo', // This is the pipe name you'll use in templates
})
export class YesNoPipe implements PipeTransform {
  transform(value: any): string {
    if (value === true) {
      return 'Yes';
    } else if (value === false) {
      return 'No';
    } else {
      return ''; // Empty string for invalid inputs
    }
  }
}
