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
import { ChevronsUpDownIcon } from "lucide-react";
import { useState } from "react";
import { useTranslation } from "react-i18next";
import type { TypePeriodicData } from "../schemas/TypePeriodic";

// Setting up the props for the TypePeriodicCombobox component
type Props = {
    typePeriodic: TypePeriodicData[];
    selectedTypePeriodic: TypePeriodicData | null;
    setSelectedTypePeriodic: (type: TypePeriodicData | null) => void;
    disabled?: boolean;
};

// TypePeriodicCombobox component for selecting a type periodic from a list
export function TypePeriodicCombobox({
  typePeriodic,
  selectedTypePeriodic,
  setSelectedTypePeriodic,
  disabled = false,
}: Props) {

  // State to manage the open/close state of the popover
  const [open, setOpen] = useState(false);
  const { t } = useTranslation();
  return (
    // Popover component to display the combobox
    <Popover open={open} onOpenChange={(value) => !disabled && setOpen(value)}>
      <PopoverTrigger asChild>
        {/* Trigger button for the popover */}
        <Button
          type="button"
          variant="outline"
          role="combobox"
          aria-expanded={open}
          disabled={disabled}
          className="category-selector-button"
        >
          {selectedTypePeriodic ? t(`domains.typePeriodic.${selectedTypePeriodic.name}`) : t("domains.spending.add.selectTypePeriodic")}
          {/* Icon to indicate dropdown functionality */}
          <ChevronsUpDownIcon className="chevrons-up-down-icon" />
        </Button>
      </PopoverTrigger>
      {/* Content of the popover containing the command list */}
      <PopoverContent className="category-popover-content">
        <Command className="bg-zinc-900 text-white">
          <CommandInput
            placeholder={t("domains.spending.add.searchTypePeriodic")}
            className="category-command-input"
          />
          <CommandList>
            <CommandEmpty className="text-white">{t("domains.spending.add.noTypePeriodic")}</CommandEmpty>
            <CommandGroup className="border-t-0">
              {typePeriodic.map((type) => (
                <CommandItem
                  key={type.id}
                  value={String(type.name)}
                  onSelect={() => {
                    setSelectedTypePeriodic(type);
                    setOpen(false);
                  }}
                  className="text-white hover:!bg-zinc-700 hover:!text-white cursor-pointer"
                >
                  {t(`domains.typePeriodic.${type.name}`)}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
}
