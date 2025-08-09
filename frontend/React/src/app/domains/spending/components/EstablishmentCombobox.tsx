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
import type { EstablishmentData } from "../schemas/EstablishmentData";

// Setting up the props for the TypePeriodicCombobox component
type Props = {
    establishments: EstablishmentData[];
    selectedEstablishment: EstablishmentData | null;
    setSelectedEstablishment: (establishment: EstablishmentData | null) => void;
    disabled?: boolean;
};

// EstablishmentCombobox component for selecting an establishment from a list
export function EstablishmentCombobox({
  establishments,
  selectedEstablishment,
  setSelectedEstablishment,
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
          {!selectedEstablishment ? t("domains.spending.add.selectEstablishment") : selectedEstablishment.id === 0 ? t("domains.spending.add.selectedNewEstablishment") : selectedEstablishment.name}
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
            <CommandEmpty className="text-white">{t("domains.spending.add.noEstablishment")}</CommandEmpty>
            <CommandGroup className="border-t-0">
              {establishments.map((establishment) => (
                <CommandItem
                  key={establishment.id}
                  value={String(establishment.name)}
                  onSelect={() => {
                    setSelectedEstablishment(establishment);
                    setOpen(false);
                  }}
                  className="text-white hover:!bg-zinc-700 hover:!text-white cursor-pointer"
                >
                  {establishment.id === 0
                    ? t("domains.spending.add.newEstablishment")
                    : `${establishment.name}, ${establishment.country}, ${establishment.city}`}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
}
