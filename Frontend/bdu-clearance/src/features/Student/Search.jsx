import FormRow from "../../ui/components/FormRow";
import Input from "../../ui/components/Input";
import RadioInput from "../../ui/components/RadioInput";
import PageHeader from "../../ui/components/PageHeader";
import CheckboxInput from "../../ui/components/CheckboxInput";
import { Bed, BookOpen, Coffee, LibraryIcon, UserCheck2 } from "lucide-react";

function Search() {
  return (
    <container className="max-w-4xl  mx-auto my-6 flex flex-col  gap-4  bg-white border-gray-200 border p-4 lg:p-8 font-sans text-slate-800  rounded-xl shadow-lg">
      <PageHeader
        title="Clearance Request Form"
        description="Please provide all required informations accurately."
      />
      <form className="relative grow sm:grow-0 w-full sm:w-auto">
        <section className="bg-light p-4 sm:p-6 rounded-2xl mb-6 ">
          <h2 className="text-xl text-blueish font-semibold mb-3">
            Academic informations
          </h2>

          <div className="flex gap-4 flex-col sm:flex-row">
            <FormRow label="Year of Study" error="">
              <Input type="number" placeholder="e.g., 4" />
            </FormRow>
            <FormRow label="Semester" error="">
              <Input type="text" placeholder="e.g., 1st Sem" />
            </FormRow>
          </div>

          <h2 className="text-normal text-gray-600 font-medium mt-6">
            Reason of clearance
          </h2>
          <div className="mt-1 bg-gray-50 p-4 rounded-lg ">
            <RadioInput
              label="End of academic year"
              id="leave"
              type="radio"
              name="reason"
            />
            <RadioInput
              label="Graduation"
              id="graduation"
              type="radio"
              name="reason"
            />
            <RadioInput
              label="Withdraw"
              id="withdraw"
              type="radio"
              name="reason"
            />
            <RadioInput
              label="Dicipline case"
              id="dicipline"
              type="radio"
              name="reason"
            />
            <RadioInput label="other" id="other" type="radio" name="reason" />
          </div>
          <span className="">
            <p>Specify your Reason</p>
            <Input type="text" placeholder="specific reason..." />
          </span>
        </section>
        <PageHeader title="Required Clearance Units" />
        <section className="flex flex-col gap-2 mb-6">
          <CheckboxInput
            label="Library"
            icon={<LibraryIcon size={25} />}
            iconColor="text-yellow-600"
          />
          <CheckboxInput
            label="Cafeteria"
            icon={<Coffee size={25} />}
            iconColor="text-gray-600"
          />
          <CheckboxInput
            label="Dormitory"
            icon={<Bed size={25} />}
            iconColor="text-red-600"
          />
          <CheckboxInput
            label="Registrar"
            icon={<UserCheck2 size={25} />}
            iconColor="text-blue-600"
          />
          <CheckboxInput
            label="Faculty"
            icon={<BookOpen size="24" />}
            iconColor="text-purple-600"
          />
        </section>
        <Input type="submit" />
      </form>
    </container>
  );
}

export default Search;
