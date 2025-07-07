import emojiData from '@emoji-mart/data';

// Function to retrieve the native emoji (e.g. "💲") from its ID (e.g. "heavy_dollar_sign")
export function getEmojiById(id: string): string | undefined {
  // Access the emojis map from the data object
  const emojiEntry = (emojiData as any).emojis?.[id];

  // Return the native emoji character from the first skin (most common form)
  return emojiEntry?.skins?.[0]?.native;
}
