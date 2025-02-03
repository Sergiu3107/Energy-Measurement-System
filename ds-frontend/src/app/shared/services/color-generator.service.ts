import {Injectable} from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ColorGeneratorService {

  getUserColor(userId: string): string {
    // Retrieve the stored color using a key specific to the user ID
    let storedColor = localStorage.getItem(`userColor_${userId}`);
    if (storedColor) {
      return storedColor; // Return the stored color if available
    }

    const newColor = this.generateRandomColor();
    localStorage.setItem(`userColor_${userId}`, newColor); // Store the color directly as a string
    return newColor;
  }

  private generateRandomColor(): string {
    const letters = '0123456789ABCDEF';
    let color = '#';
    for (let i = 0; i < 6; i++) {
      color += letters[Math.floor(Math.random() * 16)];
    }
    return color;
  }
}
