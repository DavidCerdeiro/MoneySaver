import emojiData from '@emoji-mart/data';

// Function to retrieve the native emoji (e.g. "💲") from its ID (e.g. "heavy_dollar_sign")
export function getEmojiById(id?: string): string {
  if (!id) return '';
  const emojiEntry = (emojiData as any).emojis?.[id];
  return emojiEntry?.skins?.[0]?.native || '';
}
