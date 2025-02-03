import {Pipe, PipeTransform} from '@angular/core';
import {User} from '../models/user';
import {Device} from '../models/device';

@Pipe({
  standalone: true,
  name: 'filterDevices'
})
export class FilterUsersPipe implements PipeTransform {
  transform(items: Device[] | undefined, searchText: string): any[] | undefined {
    if (!items) return [];
    if (searchText == '') return items;

    searchText = searchText.toLowerCase();
    return items.filter((item: Device) => {
        return item.description.toLowerCase().includes(searchText) ||
        item.address.toLowerCase().includes(searchText)
    });


  }
}
