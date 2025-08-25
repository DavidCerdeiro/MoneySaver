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
import { useState } from "react";
import { useTranslation } from "react-i18next";
import type { GoalData } from "../schemas/Goal";

type Props = {
  goals: GoalData[];
  selectedGoal: GoalData | null;
  setSelectedGoal: (goal: GoalData | null) => void;
};

export function GoalCombobox({
  goals,
  selectedGoal,
  setSelectedGoal,
}: Props) {
  const [open, setOpen] = useState(false);
  const { t } = useTranslation();
  return (
    // Popover component to display the combobox
    <Popover open={open} onOpenChange={setOpen}>
      <PopoverTrigger asChild>
        {/* Trigger button for the popover */}
        <Button
          type="button"
          variant="outline"
          role="combobox"
          aria-expanded={open}
          className="combobox-selector-button"
        >
          {selectedGoal?.name || t("domains.goal.combobox.select")}
          <ChevronsUpDownIcon className="ml-2 h-4 w-4 shrink-0 opacity-50" />
        </Button>
    </PopoverTrigger>
      {/* Content of the popover containing the command list */}
      <PopoverContent className="combobox-popover-content">
        <Command className="bg-zinc-900 text-white">
          <CommandInput
            placeholder={t("domains.goal.combobox.search")}
            className="combobox-command-input"
          />
          <CommandList>
            <CommandEmpty className="text-white">{t("domains.goal.combobox.noGoals")}</CommandEmpty>
            <CommandGroup className="border-t-0">
              {goals.map((goal) => (
                <CommandItem
                  key={goal.id}
                  value={String(goal.name)}
                  onSelect={() => {
                    setSelectedGoal(goal);
                    setOpen(false);
                  }}
                  className="text-white hover:!bg-zinc-700 hover:!text-white cursor-pointer"
                >
                  <CheckIcon
                    className={cn(
                      "combobox-check-icon text-current",
                      selectedGoal?.name === goal.name ? "opacity-100" : "opacity-0"
                    )}
                  />
                  {goal.name}
                </CommandItem>
              ))}
            </CommandGroup>
          </CommandList>
        </Command>
      </PopoverContent>
    </Popover>
  );
}
