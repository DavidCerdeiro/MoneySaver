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
import type { TypeChartData } from "../schemas/TypeChart";

// Setting up the props for the TypeChartCombobox component
type Props = {
    typeChart: TypeChartData[];
    selectedTypeChart: TypeChartData | null;
    setSelectedTypeChart: (type: TypeChartData | null) => void;
    disabled?: boolean;
};

// TypeChartCombobox component for selecting a type chart from a list
export function TypeChartCombobox({
  typeChart,
  selectedTypeChart,
  setSelectedTypeChart,
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
          className="combobox-selector-button"
        >
          {selectedTypeChart ? t(`domains.charts.type.${selectedTypeChart.name}`) : t("domains.charts.type.combobox.select")}
          {/* Icon to indicate dropdown functionality */}
          <ChevronsUpDownIcon className="chevrons-up-down-icon" />
        </Button>
      </PopoverTrigger>
      {/* Content of the popover containing the command list */}
      <PopoverContent className="combobox-popover-content">
        <Command className="bg-zinc-900 text-white">
          <CommandInput
            placeholder={t("domains.charts.type.combobox.search")}
            className="combobox-command-input"
          />
          <CommandList>
            <CommandEmpty className="text-white">{t("domains.charts.type.combobox.noTypeChart")}</CommandEmpty>
            <CommandGroup className="border-t-0">
              {typeChart.map((type) => (
                <CommandItem
                  key={type.id}
                  value={String(type.name)}
                  onSelect={() => {
                    setSelectedTypeChart(type);
                    setOpen(false);
                  }}
                  className="text-white hover:!bg-zinc-700 hover:!text-white cursor-pointer"
                >
                  {t(`domains.charts.type.${type.name}`)}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
}
