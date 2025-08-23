import {
  Popover,
  PopoverContent,
  PopoverTrigger,
} from "@/app/domains/shared/components/popover";
import {
  Command,
  CommandEmpty,
  CommandGroup,
  CommandInput,
  CommandItem,
  CommandList,
} from "@/app/domains/shared/components/command";
import { Button } from "@/app/domains/shared/components/button";
import { ChevronsUpDownIcon, CheckIcon } from "lucide-react";
import { cn } from "@/app/domains/shared/lib/utils";
import type { CategoryData } from "../schemas/Category";
import { useState } from "react";
import { useTranslation } from "react-i18next";

type Props = {
  categories: CategoryData[];
  selectedCategory: CategoryData | null;
  setSelectedCategory: (cat: CategoryData | null) => void;
  setSelectedEmoji: (emoji: string) => void;
  setSelectedIdEmoji: (id: string) => void;
  setEmojiIsNative: (value: boolean) => void;
  disabled: boolean;
};

export function CategoryCombobox({
  categories,
  selectedCategory,
  setSelectedCategory,
  setSelectedEmoji,
  setSelectedIdEmoji,
  setEmojiIsNative,
  disabled = false,
}: Props) {
  const [open, setOpen] = useState(false);
  const { t } = useTranslation();
  return (
    // Popover component to display the combobox
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild disabled={disabled}>
        {/* Trigger button for the popover */}
        <Button
          type="button"
          variant="outline"
          role="combobox"
          aria-expanded={open}
          className="combobox-selector-button"
        >
          {selectedCategory?.name || t("domains.category.modify.selectCategory")}
          <ChevronsUpDownIcon className="ml-2 h-4 w-4 shrink-0 opacity-50" />
        </Button>
    </PopoverTrigger>
      {/* Content of the popover containing the command list */}
      <PopoverContent className="combobox-popover-content">
        <Command className="bg-zinc-900 text-white">
          <CommandInput
            placeholder="Search category..."
            className="combobox-command-input"
          />
          <CommandList>
            <CommandEmpty className="text-white">{t("domains.category.modify.noCategories")}</CommandEmpty>
            <CommandGroup className="border-t-0">
              {categories.map((category) => (
                <CommandItem
                  key={category.id}
                  value={String(category.name)}
                  onSelect={() => {
                    setSelectedCategory(category);
                    setOpen(false);
                    setSelectedEmoji(category.icon || "");
                    setEmojiIsNative(false);
                    setSelectedIdEmoji(category.icon || "");
                  }}
                  className="text-white hover:!bg-zinc-700 hover:!text-white cursor-pointer"
                >
                  <CheckIcon
                    className={cn(
                      "combobox-check-icon text-current",
                      selectedCategory?.name === category.name ? "opacity-100" : "opacity-0"
                    )}
                  />
                  {category.name}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
}
