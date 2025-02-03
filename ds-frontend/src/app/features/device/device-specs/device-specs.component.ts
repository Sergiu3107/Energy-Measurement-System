import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {DeviceService} from '../../../core/services/device.service';
import {Device} from '../../../core/models/device';
import {NgIf, NgTemplateOutlet} from '@angular/common';

@Component({
  selector: 'app-device-specs',
  standalone: true,
  imports: [
    FormsModule,
    ReactiveFormsModule,
    NgIf,
    NgTemplateOutlet
  ],
  templateUrl: './device-specs.component.html',
  styleUrl: './device-specs.component.css'
})
export class DeviceSpecsComponent implements OnInit {
  deviceForm: any;
  selectedDevice: Device | null = null;
  addEnable: boolean = false;
  editEnable: boolean = false;

  constructor(private deviceService: DeviceService) {
  }

  ngOnInit(): void {
    this.deviceForm = new FormGroup({
      description: new FormControl({value: '', disabled: !this.addEnable && !this.editEnable}, [Validators.required]),
      address: new FormControl({value: '', disabled: !this.addEnable && !this.editEnable}, [Validators.required]),
      costPerHour: new FormControl({value: '', disabled: !this.addEnable && !this.editEnable}, [Validators.required]),
    })

    this.onDeviceSelect();
  }

  private onDeviceSelect() {
    this.deviceService.selectedDevice.asObservable().subscribe(device => {
      this.selectedDevice = device;
      this.updateForm(device);
    })
  }

  private updateForm(device: Device | null) {
    if (device) {
      this.deviceForm.patchValue({
        description: device.description,
        address: device.address,
        costPerHour: device.costPerHour,
      });
    } else {
      this.deviceForm.reset();
    }
  }

  onSelectModel(mode: string) {
    this.addEnable = mode === "add";
    this.editEnable = mode === "edit";
    this.reEnableForm();
  }

  onCancel() {
    this.disEnableForm();
    this.editEnable = false;
    this.addEnable = false;
  }

  onSave() {
    if (this.deviceForm.valid) {
      const description = this.deviceForm.get('description').value;
      const address = this.deviceForm.get('address').value;
      const costPerHour = this.deviceForm.get('costPerHour').value;
      this.deviceService.addDevice({description, address, costPerHour}).subscribe()
    }
  }

  onUpdate() {
    if (this.deviceForm.valid) {
      const description = this.deviceForm.get('description').value;
      const address = this.deviceForm.get('address').value;
      const costPerHour = this.deviceForm.get('costPerHour').value;

      if(this.selectedDevice) {
        const id = this.selectedDevice.id;
        console.log(this.deviceForm.value)
        this.deviceService.updateDevice( id, {description, address, costPerHour}).subscribe();
      }
    }
  }

  clearForm() {
    this.deviceForm.reset();
  }

  private reEnableForm() {
    this.deviceForm.get('description')?.enable();
    this.deviceForm.get('address')?.enable();
    this.deviceForm.get('costPerHour')?.enable();
  }

  private disEnableForm() {
    this.deviceForm.get('description')?.disable();
    this.deviceForm.get('address')?.disable();
    this.deviceForm.get('costPerHour')?.disable();
  }
}
